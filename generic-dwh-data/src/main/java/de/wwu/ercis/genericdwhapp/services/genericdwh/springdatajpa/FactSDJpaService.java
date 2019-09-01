package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.FactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class FactSDJpaService implements FactService {

    private final FactRepository factRepository;
    private final RatioRepository ratioRepository;
    private final DimensionRepository dimensionRepository;
    private final ReferenceObjectRepository referenceObjectRepository;
    private final DimensionCombinationRepository dimensionCombinationRepository;

    private String queryMethod ="";
    private String executedQuery ="";

    public FactSDJpaService(FactRepository factRepository, RatioRepository ratioRepository, DimensionRepository dimensionRepository, ReferenceObjectRepository referenceObjectRepository, DimensionCombinationRepository dimensionCombinationRepository) {
        this.factRepository = factRepository;
        this.ratioRepository = ratioRepository;
        this.dimensionRepository = dimensionRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.dimensionCombinationRepository = dimensionCombinationRepository;
    }

    @Override
    public List<Fact> findAll() {
        List<Fact> facts = new ArrayList<>();
        factRepository.findAll().forEach(facts::add);
        return facts;
    }

    @Override
    public List<Fact> findAllSort(Sort sort) {
        List<Fact> facts = new ArrayList<>();
        factRepository.findAll(sort).forEach(facts::add);
        return facts;
    }

    @Override
    public Fact findById(Long aLong) {
        return factRepository.findById(aLong).orElse(null);
    }

    @Override
    public Fact save(Fact object) { return factRepository.save(object);}

    @Override
    public Fact findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio) {
        return factRepository.findByReferenceObjectAndRatio(referenceObject, ratio).orElse(null);
    }

    @Override
    public List<Fact> dynETLQuery(List<String> ratios, List<String> dimensions, List<String> dCombinations) {

        queryMethod="";
        Dimension dimensionResult = new Dimension();
        Ratio ratioResult;
        List<Fact> factsResult = new ArrayList<Fact>();

        // uses SQL custom query
        for (String dimension_root : dCombinations) {
            List<Ratio> ratioList = ratioRepository.findByDimensionCombinationId(Long.parseLong(dimension_root));
            for (String radio_id : ratios) {
                ratioResult = ratioRepository.findById((Long.parseLong(radio_id))).orElse(null);
                if (ratioList.contains(ratioResult)){
                    for (String dimension_id : dimensions) {
                        dimensionResult.setId(Long.parseLong(dimension_id));
                        ReferenceObject roResult = referenceObjectRepository.findFirstByDimension(dimensionResult).orElse(null);
                        if (null == this.findByReferenceObjectAndRatio(roResult,ratioResult)  ) {
                            this.genericDWHResults(dimension_id, radio_id, dimension_root).forEach(factsResult::add);
                            factsResult.forEach(fact -> factRepository.insertNewFact(fact.getReferenceObjectId(),fact.getRatioId(),fact.getValue()));
                            if (queryMethod.startsWith("R") && !queryMethod.contains("and"))
                                queryMethod = queryMethod + " and New Facts inserted";
                            else queryMethod = "New Facts inserted";
                        }
                        else {
                            this.findByDimensionIdAndRatioId(dimension_id, radio_id).forEach(factsResult::add);
                            if (queryMethod.startsWith("N") && !queryMethod.contains("and"))
                                queryMethod = queryMethod + " and Returned Existing Facts";
                            else queryMethod = "Returned Existing Facts";
                        }
                    }
                }
            }
        }
        return factsResult;
    }

    @Override
    public String queryMethod() {
        return queryMethod;
    }

    @Override
    public String query() { return executedQuery; }

    @Override
    public List<Fact> findByDimensionIdAndRatioId(String dimensionId, String ratioId) {
        return factRepository.findByDimensionIdAndRatioId(dimensionId, ratioId);
    }

    @Override
    public List<Fact> genericDWHResults(String dimensionId, String ratioId, String dimensionCombination) {
        return factRepository.genericDWHResults(dimensionId, ratioId, dimensionCombination);
    }

    @Override
    public List<String[]> gdwhDynQuery(List<String> ratios, List<String> dimensions) {
        queryMethod = "";
        List<String[]> factsResult = new ArrayList<>();
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        //split ratios and dimension combinations
        Map<String, String> dCombinationsMap = ratios
                .stream()
                .distinct()
                .map(s -> s.split("_"))
                .collect(toMap(s -> s[0], s -> s[1]));

        //add unique dimension combinations and all ratios to lists
        dCombinationsMap.forEach((ratioId, dimension_combinationId) -> {
            if (!dCombinations.contains(dimension_combinationId))
                dCombinations.add(dimension_combinationId);
            ratiosResult.add(ratioRepository.findById(Long.parseLong(ratioId)).orElse(null));
        });
        List<Long> dimensionsIds = dimensions.stream().map(s -> Long.parseLong(s)).distinct().sorted().collect(Collectors.toList());
        dimensionsIds.stream().sorted().forEach(dId -> dimensionsResult.add(dimensionRepository.findById(dId).orElse(null)));

        String query = "";
        List<String> selects = new ArrayList<>();
        List<String> selectsWithSubstring = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        String roJoins = "";
        String ratiosJoins ="";
        String where = "";
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();

        for (Ratio ratio : ratiosResult) {
            String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
            ratiosJoins = ratiosJoins + "INNER JOIN fact " + ratioQuery + " ON "
                    + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id=" + ratio.getId() + "\n";
        }

        for (Dimension dimension : dimensionsResult) {
            String dimensionQuery = dimension.getName().replaceAll(" ", "_").toLowerCase();
            groupBy.add("_" + dimensionQuery + ".id");
            orderBy.add("_" + dimensionQuery + ".name");
            selects.add("_" + dimensionQuery + ".name");
            roJoins = roJoins +
                    "INNER JOIN reference_object_combination _ro_" + dimensionQuery + " ON _ro_" + dimensionQuery + ".combination_id = ro.id\n" +
                    "INNER JOIN reference_object _" + dimensionQuery + " ON _" + dimensionQuery + ".id = _ro_"
                    + dimensionQuery + ".subordinate_id AND _" + dimensionQuery + ".dimension_id = " + dimension.getId() + " \n";
        }

        if (dimensionsResult.size() == 1) {
            where = "WHERE ro.dimension_id =" + dimensions.get(0) + " ORDER BY ro.name";
            query = "SELECT ro.name, " + ratiosResult
                    .stream()
                    .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2)")
                    .collect(Collectors.joining(","))
                    + from + ratiosJoins + where;
            log.debug("query from dimension == 1");
            System.out.println(query);
            factsResult = factRepository.nativeQuery(query);
            executedQuery = query;
            queryMethod = "Returned existing facts.";

            if (factsResult.isEmpty()) {
                factsResult = gdwhStdQuery(ratios, dimensions);
                queryMethod = "New facts inserted.";
                for (String dcId : dCombinations) {
                    for (Ratio ratio : ratiosResult) {
                        insertNewFacts(dimensionsIds.get(0).toString(), ratio.getId().toString(), dcId);
                    }
                }
            }
        }
        else {
            for (int i = 0; i < dimensionsResult.size(); i++) {
                String dimensionQuery = dimensionsResult.get(i).getName().replaceAll(" ", "_").toLowerCase();
                selectsWithSubstring.add("SUBSTRING_INDEX(SUBSTRING_INDEX(ro.name,','," + (i + 1) + "),',',-1) as '" + dimensionQuery + "'");
            }

            for (Ratio ratio : ratiosResult) {
                //check if selected dimension combination exists
                String dimensionName = dimensionsResult.stream().map(d -> d.getName()).collect(Collectors.joining(", "));
                Dimension existingDimensionCombination = dimensionRepository.findByName(dimensionName).orElse(null);
                // if the dimension combination selected by the user doesn't exist, insert new
                Dimension newDimensionCombination;
                if (existingDimensionCombination == null) {
                    Dimension newCombination = new Dimension(dimensionName, false);
                    newDimensionCombination = dimensionRepository.save(newCombination);
                    // create new dimension combinations for each dimension
                    dimensionsResult.forEach(d -> dimensionCombinationRepository.save(new DimensionCombination(newDimensionCombination.getId(), d.getId())));
                    where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ")\n";
                    // create new ref objects and facts
                    String concatWSQuery = "SELECT CONCAT_WS(\", \", " + selects.stream().collect(Collectors.joining(", "))
                            + ") as 'roName', sum(" + ratio.getName().toLowerCase().replaceAll(" ", "_") + ".value) "
                            + from + roJoins + ratiosJoins + where
                            + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                            + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(","));
                    log.debug("concatws query");
                    System.out.println(concatWSQuery);
                    factsResult = factRepository.nativeQuery(concatWSQuery);
                    executedQuery = concatWSQuery;
                    if (!factsResult.isEmpty()) {
                        List<Fact> facts = new ArrayList<>();
                        factsResult.stream().forEach(f -> {
                            Fact fact = new Fact();
                            ReferenceObject ro = referenceObjectRepository.save(new ReferenceObject(f[0].toString(), newDimensionCombination, "", false));
                            System.out.println(ro.getId());
                            fact.setReferenceObjectId(ro.getId());
                            fact.setRatioId(ratio.getId());
                            fact.setValue(Double.valueOf(f[1]));
                            facts.add(fact);
                        });
                        factRepository.saveAll(facts);
                        queryMethod = "New facts inserted.";
                        System.out.println("facts saved at concatWSQuery!");
                    }

                    where = " WHERE ro.dimension_id =" + newDimensionCombination.getId() + " ORDER BY ro.name";
                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(","))
                            + from + ratiosJoins + where;
                    log.debug("substring query after inserting facts");
                    System.out.println(substringQuery);
                    factsResult = factRepository.nativeQuery(substringQuery);
                }
                // if there's already a dimension combination, check if for each selected ratio if there are facts saved
                else if((checkFactsForDimensionCombinationAndRatio(ratio.getId(), existingDimensionCombination.getId()) == false)) {
                    //insert new facts for an existing dimension combination which has already its reference objects, but has no saved facts for this ratio
                    String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
                    String singleRatioJoin = "INNER JOIN fact " + ratioQuery + " ON "
                            + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id=" + ratio.getId() + "\n";
                    where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ")\n";
                    String dirtyJoinQuery = "SELECT b.reference_object_id, a.ratioId, a.ratioValue from\n" +
                            "(SELECT CONCAT_WS(\", \", " + selects.stream().collect(Collectors.joining(", "))
                            + ") as 'roNameValue', sum(" + ratio.getName().toLowerCase().replaceAll(" ", "_") + ".value) as 'ratioValue', "
                            + ratioQuery +".ratio_id AS 'ratioId' \n"
                            + from + roJoins + singleRatioJoin + where
                            + " GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                            + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(",")) + ") a \n"
                            + "INNER JOIN \n" +
                            "(SELECT ro.id AS 'reference_object_id', ro.name AS 'roNameNoValue' FROM reference_object ro \n" +
                            " WHERE ro.dimension_id =" + existingDimensionCombination.getId() + ") b\n" +
                            " ON a.roNameValue = b.roNameNoValue";
                    log.debug("read and insert from dirty query");
                    System.out.println(dirtyJoinQuery);
                    List<String[]> newFactsResult = factRepository.nativeQuery(dirtyJoinQuery);
                    if (!newFactsResult.isEmpty()) {
                        List<Fact> facts = new ArrayList<>();
                        newFactsResult.stream().forEach(f -> {
                            Fact fact = new Fact();
                            fact.setReferenceObjectId(Long.valueOf(f[0]));
                            fact.setRatioId(Long.valueOf(f[1]));
                            fact.setValue(Double.valueOf(f[2]));
                            facts.add(fact);
                        });
                        factRepository.saveAll(facts);
                        System.out.println("facts saved after dirty join!");
                    }

                    where = " WHERE ro.dimension_id =" + existingDimensionCombination.getId() + " ORDER BY ro.name";
                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(","))
                            + from + ratiosJoins + where;
                    log.debug("substring query after inserting facts on existing reference objects");
                    System.out.println(substringQuery);
                    factsResult = factRepository.nativeQuery(substringQuery);
                    queryMethod = "New facts inserted.";
                    executedQuery = dirtyJoinQuery;
                }
                else {
                    String substringQuery = "";
                    //there are already saved facts for this ratio and dimension combination
                    where = " WHERE ro.dimension_id =" + existingDimensionCombination.getId() + " ORDER BY ro.name";
                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(",")) + from + ratiosJoins + where;
                    log.debug("substring query ratios > 1");
                    System.out.println(substringQuery);
                    factsResult = factRepository.nativeQuery(substringQuery);
                    executedQuery = substringQuery;
                    queryMethod = "Returned existing facts.";
                }
            }
        }

        // change IDs to names to display on fronted
        ratios.clear();
        ratiosResult.forEach(ratio -> ratios.add(ratio.getName()));
        dimensions.clear();
        dimensionsResult.forEach( dimension -> dimensions.add(dimension.getName()));

        return factsResult;
    }

    @Override
    public List<String[]> gdwhStdQuery(List<String> ratios, List<String> dimensions) {
        queryMethod="";
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        //split ratios and dimension combinations, they come together from frontend
        Map<String,String> dCombinationsMap = ratios
                .stream()
                .distinct()
                .map(s -> s.split("_"))
                .collect(toMap(s -> s[0], s -> s[1]));

        //add unique dimension combinations and all ratios to lists
        dCombinationsMap.forEach( (ratioId,dimension_combinationId) -> {
            if(!dCombinations.contains(dimension_combinationId))
                dCombinations.add(dimension_combinationId);
            ratiosResult.add(ratioRepository.findById(Long.parseLong(ratioId)).orElse(null));
        });
        ratios.clear();
        ratiosResult.forEach(ratio -> ratios.add(ratio.getName()));
        dimensions.stream().distinct().forEach( d -> dimensionsResult.add(dimensionRepository.findById(Long.parseLong(d)).orElse(null)));
        dimensions.clear();
        dimensionsResult.forEach( dimension -> dimensions.add(dimension.getName()));

        String query = "";
        List<String> selects = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        String joins = "";
        String where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ")\n";
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();

        for (Dimension dimension : dimensionsResult) {
            String dimensionQuery = dimension.getName().replaceAll(" ","_").toLowerCase();
            groupBy.add("_"+dimensionQuery+".id");
            orderBy.add("_"+dimensionQuery+".name");
            selects.add("_"+dimensionQuery+".name AS '"+dimensionQuery+"'");
            joins = joins +
                    "INNER JOIN reference_object_combination _ro_"+dimensionQuery+" ON _ro_"+dimensionQuery+".combination_id = ro.id\n" +
                    "INNER JOIN reference_object _"+dimensionQuery+" ON _"+dimensionQuery+".id = _ro_"
                    +dimensionQuery+".subordinate_id AND _"+dimensionQuery+".dimension_id = "+dimension.getId() +" \n";
        }
        for (Ratio ratio : ratiosResult) {
            String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
            joins = joins + "INNER JOIN fact " + ratioQuery + " ON "
                    + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id=" + ratio.getId() + "\n";
        }

        query = "SELECT " + selects.stream().collect(Collectors.joining(",")) + ", "
                + ratiosResult.stream()
                .map(r-> "FORMAT(sum("+ r.getName().toLowerCase().replaceAll(" ","_") +".value),2)")
                .collect(Collectors.joining(","))
                + from + joins + where
                + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(","));

        executedQuery = query;

        return factRepository.nativeQuery(query);
    }

    public void insertNewFacts(String dimension, String ratio, String dimensionCombination){

        String query = "SELECT _ref_object.id, _ratio.ratio_id, sum(_ratio.value)\n" +
                "FROM reference_object ro \n" +
                "INNER JOIN reference_object_combination _ro_combination ON _ro_combination.combination_id = ro.id \n" +
                "INNER JOIN reference_object _ref_object ON _ref_object.id = _ro_combination.subordinate_id AND _ref_object.dimension_id = " +dimension+ "\n" +
                "INNER JOIN fact _ratio ON _ratio.reference_object_id = ro.id AND _ratio.ratio_id = " +ratio+ "\n" +
                "WHERE ro.dimension_id = "+dimensionCombination+ "\n" +
                "GROUP BY _ref_object.id \n" +
                "ORDER BY _ref_object.id ";

        List<String[]> factsResult = factRepository.nativeQuery(query);
        if (!factsResult.isEmpty()){
            List<Fact> facts = new ArrayList<>();
            factsResult.parallelStream().forEach(f -> {
                Fact fact = new Fact();
                fact.setReferenceObjectId(Long.valueOf(f[0]));
                fact.setRatioId(Long.valueOf(f[1]));
                fact.setValue(Double.valueOf(f[2]));
                facts.add(fact);
            });

            factRepository.saveAll(facts);
            System.out.println("facts saved!");
        }

    }

    public boolean checkFactsForDimensionCombinationAndRatio(Long ratioId, Long dimensionCombinationId){

        String query = "SELECT f.value FROM fact f \n" +
                "WHERE f.ratio_id = "+ratioId+" and f.reference_object_id =\n" +
                "(SELECT id FROM reference_object\n" +
                "WHERE dimension_id = "+dimensionCombinationId+" LIMIT 1)\n" +
                "LIMIT 1 ";

        List<String[]> factsResult = factRepository.nativeQuery(query);
        if (factsResult.isEmpty())
            return false;
        else
            return true;
    }

}
