package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import de.wwu.ercis.genericdwhapp.services.genericdwh.FactService;
import lombok.extern.slf4j.Slf4j;
import org.paukov.combinatorics3.Generator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class FactSDJpaService implements FactService {

    private final FactRepository factRepository;
    private final RatioRepository ratioRepository;
    private final DimensionRepository dimensionRepository;
    private final ReferenceObjectRepository referenceObjectRepository;
    private final DimensionCombinationRepository dimensionCombinationRepository;

    private final DimensionHierarchyService dimensionHierarchyService;

    private String queryMethod ="";
    private String executedQuery ="";
    private String limit = " LIMIT 1000 ";

    public FactSDJpaService(FactRepository factRepository, RatioRepository ratioRepository, DimensionRepository dimensionRepository, ReferenceObjectRepository referenceObjectRepository, DimensionCombinationRepository dimensionCombinationRepository, DimensionHierarchyService dimensionHierarchyService) {
        this.factRepository = factRepository;
        this.ratioRepository = ratioRepository;
        this.dimensionRepository = dimensionRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.dimensionCombinationRepository = dimensionCombinationRepository;
        this.dimensionHierarchyService = dimensionHierarchyService;
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
    public String queryMethod() {
        return queryMethod;
    }

    @Override
    public String query() {
        String result = executedQuery.toUpperCase().replaceAll("\n","<br/>");
        result = result.replace("SELECT","<a class=\"text-primary font-weight-bold\">SELECT</a>");
        result = result.replace("FROM REFERENCE_OBJECT RO","<a class=\"text-danger font-weight-bold\">FROM REFERENCE_OBJECT RO</a>");
        result = result.replace("INNER JOIN REFERENCE_OBJECT_COMBINATION","<a class=\"text-primary font-weight-bold\">INNER JOIN REFERENCE_OBJECT_COMBINATION</a>");
        result = result.replace("INNER JOIN REFERENCE_OBJECT_HIERARCHY","<a class=\"text-danger font-weight-bold\">INNER JOIN REFERENCE_OBJECT_HIERARCHY</a>");
        result = result.replace("INNER JOIN FACT","<a class=\"text-primary font-weight-bold\">INNER JOIN FACT</a>");
        result = result.replace("WHERE","<a class=\"text-danger font-weight-bold\">WHERE</a>");
        result = result.replace("GROUP BY","<a class=\"text-danger font-weight-bold\">GROUP BY</a>");
        result = result.replace("ORDER BY","<a class=\"text-danger font-weight-bold\">ORDER BY</a>");
        result = result.replace("CONCAT_WS","<a class=\"text-danger font-italic\">CONCAT_WS</a>");
        result = result.replace("SUBSTRING_INDEX","<a class=\"text-success font-weight-bold\">SUBSTRING_INDEX</a>");
        return result;
    }

    @Override
    public List<String[]> gdwhDynQuery(List<String> ratios, List<String> dimensions, List<String> filters) {
        queryMethod = "";
        List<String[]> factsResult = new ArrayList<>();
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        List<Long> dimensionsIds = dimensions.stream().map(s -> Long.parseLong(s)).distinct().sorted().collect(Collectors.toList());

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

        String query = "";
        List<String> selects = new ArrayList<>();
        List<String> selectsWithSubstring = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        Deque<String> roJoins = new ArrayDeque<>();
        String ratiosJoins ="";
        String where = "";
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();

        for (Ratio ratio : ratiosResult) {
            String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
            ratiosJoins = ratiosJoins + "INNER JOIN fact " + ratioQuery + " ON "
                    + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id = " + ratio.getId() + "\n";
        }

        List<Dimension> atomicLevels = new ArrayList<>();
        dCombinations.forEach(dc -> {
            dimensionRepository.findAtomicLevels(dc).forEach(atomicLevels::add);
        });

        List<String> filters_where = new ArrayList<>();

        if (filters != null) {
            Map<String, String> filtersMap = filters
                    .stream()
                    .distinct()
                    .map(s -> s.split("_"))
                    .collect(toMap(s -> s[0], s -> s[1]));

            filtersMap.values().stream().distinct().forEach( dimensionId -> {

                List<String> roIds = filtersMap
                        .entrySet()
                        .stream()
                        .filter(x -> dimensionId.equals(x.getValue()))
                        .map(x -> x.getKey())
                        .collect(Collectors.toList());

                List<String> roNames = new ArrayList<>();
                for (String roId : roIds){
                    ReferenceObject referenceObject = referenceObjectRepository.findById(
                            Long.parseLong(roId)).orElse(null);
                    roNames.add(referenceObject.getName());
                }
                filters_where.add(" AND RO.NAME REGEXP '"+roNames.stream().collect(Collectors.joining("|"))+"' ");

                if(!dimensionsIds.contains(Long.parseLong(dimensionId)))
                    dimensionsIds.add(Long.parseLong(dimensionId));
            });
        }

        dimensionsIds.stream().sorted().forEach(dId -> dimensionsResult.add(dimensionRepository.findById(dId).orElse(null)));

        Deque<Dimension> dimensionHierarchy = new ArrayDeque<>();

        for (Dimension dimension : dimensionsResult) {

            if (atomicLevels.stream().filter(d -> dimension.equals(d)).findAny().orElse(null) != null && !dimensionHierarchy.contains(dimension))
                dimensionHierarchy.addFirst(dimension);

            else if (dimensionRepository.findChildByParent(dimension.getId()).orElse(null) != null ){

                Dimension child = dimensionRepository.findChildByParent(dimension.getId()).orElse(null);

                if(!dimensionHierarchy.contains(child))
                    dimensionHierarchy.addLast(child);
                while ( dimensionRepository.findChildByParent(child.getId()).orElse(null) != null ){
                    child = dimensionRepository.findChildByParent(child.getId()).orElse(null);
                    if (!dimensionHierarchy.contains(child))
                        dimensionHierarchy.addFirst(child);
                }
                if(!dimensionHierarchy.contains(dimension))
                    dimensionHierarchy.addLast(dimension);
            }
        }

        for (Dimension dh : dimensionHierarchy){
            if ( atomicLevels.stream().filter(d -> dh.equals(d)).findAny().orElse(null) != null ) {
                String atomicElement = dh.getName().replaceAll(" ","_").toLowerCase();
                roJoins.addFirst("INNER JOIN reference_object_combination _ro_"+atomicElement+" ON _ro_"+atomicElement+".combination_id = ro.id\n" +
                        "INNER JOIN reference_object _"+atomicElement+" ON _"+atomicElement+".id = _ro_"
                        +atomicElement+".subordinate_id AND _"+atomicElement+".dimension_id = "+ dh.getId() +" \n");
            }
            else {
                Dimension child = dimensionRepository.findChildByParent(dh.getId()).orElse(null);
                if (child!= null){
                    String childQuery = child.getName().replaceAll(" ", "_").toLowerCase();
                    String parentQuery = dh.getName().replaceAll(" ", "_").toLowerCase();
                    roJoins.add("INNER JOIN reference_object_hierarchy _" + childQuery + "_" + parentQuery + " ON _"
                            + childQuery + "_" + parentQuery + ".child_id = _" + childQuery + ".id\n"
                            + "INNER JOIN reference_object _" + parentQuery + " ON _" + parentQuery + ".id = _"
                            + childQuery + "_" + parentQuery + ".parent_id AND _" + parentQuery + ".dimension_id = " + dh.getId() + " \n");
                }
            }
        }

        if (dimensionsResult.size() == 1) {

            where = "WHERE ro.dimension_id =" + dimensionsResult.get(0).getId() + filters_where.stream().collect(Collectors.joining(" "));
            query = "SELECT ro.name, " + ratiosResult
                    .stream()
                    .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2)")
                    .collect(Collectors.joining(","))
                    + from + ratiosJoins + where + "\n ORDER BY ro.name " + limit;
            factsResult = factRepository.nativeQuery(query);
            executedQuery = query;
            queryMethod = "Returned existing facts.";

            if (factsResult.isEmpty()) {
                for (String dcId : dCombinations) {
                    for (Ratio ratio : ratiosResult) {
                        if ((isEmptyFactsForDimensionCombinationAndRatio(ratio.getId(), dimensionsResult.get(0).getId()) == true)) {
                            String dimensionNameQuery = dimensionsResult.get(0).getName().replaceAll(" ", "_").toLowerCase();
                            where = "WHERE ro.dimension_id =" + dcId;
                            query = "SELECT _" + dimensionNameQuery + ".id, "
                                    + ratio.getName().replaceAll(" ", "_").toLowerCase() + ".ratio_id, "
                                    + "sum(" + ratio.getName().toLowerCase().replaceAll(" ", "_") + ".value)"
                                    + from + roJoins.stream().collect(Collectors.joining(" ")) + ratiosJoins + where
                                    + "\nGROUP BY _" + dimensionNameQuery + ".id "
                                    + "\nORDER BY _" + dimensionNameQuery + ".id "
                                    + limit;
                            factsResult = factRepository.nativeQuery(query);
                            saveFacts(factsResult);
                        }
                    }
                }
                factsResult = gdwhNcbQuery(ratios, dimensions, filters);
                queryMethod = "New facts inserted.";
            }
        }

        else {
            for (int i = 0; i < dimensionsResult.size(); i++) {
                String dimensionQuery = dimensionsResult.get(i).getName().replaceAll(" ", "_").toLowerCase();
                selectsWithSubstring.add("SUBSTRING_INDEX(SUBSTRING_INDEX(ro.name,','," + (i + 1) + "),',',-1) as '" + dimensionQuery + "'");
                selects.add("_"+dimensionQuery+".name");
                groupBy.add("_"+dimensionQuery+".id");
                orderBy.add("_"+dimensionQuery+".name");
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
                    dimensionsResult.forEach(d -> dimensionCombinationRepository.save(new DimensionCombination(newDimensionCombination.getId(), d.getId(), false)));
                    where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ")\n";
                    // create new ref objects and facts
                    String concatWSQuery = "SELECT CONCAT_WS(\", \", " + selects.stream().collect(Collectors.joining(", "))
                            + ") as 'roName', sum(" + ratio.getName().toLowerCase().replaceAll(" ", "_") + ".value) "
                            + from + roJoins.stream().collect(Collectors.joining(" ")) + ratiosJoins + where
                            + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                            + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(","))
                            + limit;
                    factsResult = factRepository.nativeQuery(concatWSQuery);
                    executedQuery = concatWSQuery;
                    if (!factsResult.isEmpty()) {
                        factsResult.forEach(f -> {
                            ReferenceObject ro = referenceObjectRepository.save(new ReferenceObject(f[0], newDimensionCombination, null, false));
                            factRepository.save(new Fact(ratio.getId(),ro.getId(),Double.valueOf(f[1])));
                        });
                        queryMethod = "New facts inserted.";
                    }

                    where = " WHERE ro.dimension_id =" + newDimensionCombination.getId();
                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(","))
                            + from + ratiosJoins + where + filters_where.stream().collect(Collectors.joining(" "))
                            + "\nORDER BY ro.name" + limit;
                    factsResult = factRepository.nativeQuery(substringQuery);
                    executedQuery = executedQuery + "\n $$$$$$ \n" + substringQuery;

                }
                // if there's already a dimension combination, check if for each selected ratio there are facts saved
                else if((isEmptyFactsForDimensionCombinationAndRatio(ratio.getId(), existingDimensionCombination.getId()) == true)) {
                    //insert new facts for an existing dimension combination which has already its reference objects, but has no saved facts for this ratio
                    String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
                    String singleRatioJoin = "INNER JOIN fact " + ratioQuery + " ON "
                            + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id = " + ratio.getId() + "\n";
                    where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ")\n";
                    String stringJoinQuery = "SELECT b.reference_object_id, a.ratioId, a.ratioValue from\n" +
                            "(SELECT CONCAT_WS(\", \", " + selects.stream().collect(Collectors.joining(", "))
                            + ") as 'roNameValue', sum(" + ratio.getName().toLowerCase().replaceAll(" ", "_") + ".value) as 'ratioValue', "
                            + ratioQuery +".ratio_id AS 'ratioId' "
                            + from + roJoins.stream().collect(Collectors.joining(" ")) + singleRatioJoin + where
                            + " GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                            + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(",")) + ") a \n"
                            + "INNER JOIN \n" +
                            "(SELECT ro.id AS 'reference_object_id', ro.name AS 'roNameNoValue' FROM reference_object ro \n" +
                            " WHERE ro.dimension_id = " + existingDimensionCombination.getId() + ") b\n" +
                            " ON a.roNameValue = b.roNameNoValue";
                    List<String[]> newFactsResult = factRepository.nativeQuery(stringJoinQuery);
                    //maybe change the insert with direct sql after returning results, don't need to send results to java and then insert,
                    //but after reading, insert using the same query
                    saveFacts(newFactsResult);

                    where = " WHERE ro.dimension_id = " + existingDimensionCombination.getId();
                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(","))
                            + from + ratiosJoins + where + filters_where.stream().collect(Collectors.joining(" "))
                            +  " \nORDER BY ro.name" + limit;
                    factsResult = factRepository.nativeQuery(substringQuery);
                    executedQuery = stringJoinQuery;
                    queryMethod = "New facts inserted.";
                }
                else {
                    //there are already saved facts for this ratio and dimension combination
                    where = "WHERE ro.dimension_id =" + existingDimensionCombination.getId()
                        + filters_where.stream().collect(Collectors.joining(" ")) + " \nORDER BY ro.name";

                    // this part uses substring_index function from mysql to split values in columns to show results on the frontend
                    String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                            + ratiosResult
                            .stream()
                            .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                                    "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                            .collect(Collectors.joining(",")) + from + ratiosJoins + where + limit;
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
        List<String> finalDimensions = dimensions;
        dimensionsResult.forEach(dimension -> finalDimensions.add(dimension.getName()));

        return factsResult;
    }

    private void saveFacts(List<String[]> factsResult) {
        if (!factsResult.isEmpty()) {
            factsResult.forEach(f -> {
                Fact fact = new Fact();
                fact.setReferenceObjectId(Long.valueOf(f[0]));
                fact.setRatioId(Long.valueOf(f[1]));
                fact.setValue(Double.valueOf(f[2]));
                factRepository.save(fact);
            });
        }
    }

    @Override
    public List<String[]> gdwhAcbQuery(List<String> ratios, List<String> dimensions, List<String> filters) {
        queryMethod = "";
        List<String[]> factsResult = new ArrayList<>();
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        List<Long> dimensionsIds = dimensions.stream().map(s -> Long.parseLong(s)).distinct().sorted().collect(Collectors.toList());

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

        List<String> selectsWithSubstring = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        String ratiosJoins ="";

        List<String> filters_where = new ArrayList<>();
        if (filters != null) {
            Map<String, String> filtersMap = filters
                    .stream()
                    .distinct()
                    .map(s -> s.split("_"))
                    .collect(toMap(s -> s[0], s -> s[1]));

            filtersMap.values().stream().distinct().forEach( dimensionId -> {

                List<String> roIds = filtersMap
                        .entrySet()
                        .stream()
                        .filter(x -> dimensionId.equals(x.getValue()))
                        .map(x -> x.getKey())
                        .collect(Collectors.toList());

                List<String> roNames = new ArrayList<>();
                for (String roId : roIds){
                    ReferenceObject referenceObject = referenceObjectRepository.findById(
                            Long.parseLong(roId)).orElse(null);
                    roNames.add(referenceObject.getName());
                }
                filters_where.add(" AND RO.NAME REGEXP '"+roNames.stream().collect(Collectors.joining("|"))+"' ");

                if(!dimensionsIds.contains(Long.parseLong(dimensionId)))
                    dimensionsIds.add(Long.parseLong(dimensionId));
            });
        }

        dimensionsIds.stream().sorted().forEach(dId -> dimensionsResult.add(dimensionRepository.findById(dId).orElse(null)));

        for (Ratio ratio : ratiosResult) {
            String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
            ratiosJoins = ratiosJoins + "INNER JOIN fact " + ratioQuery + " ON "
                    + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id = " + ratio.getId() + "\n";
        }

        for (int i = 0; i < dimensionsResult.size(); i++) {
            String dimensionQuery = dimensionsResult.get(i).getName().replaceAll(" ", "_").toLowerCase();
            selectsWithSubstring.add("SUBSTRING_INDEX(SUBSTRING_INDEX(ro.name,','," + (i + 1) + "),',',-1) as '" + dimensionQuery + "'");
        }

        //check if selected dimension combination exists
        String dimensionName = dimensionsResult.stream().map(d -> d.getName()).collect(Collectors.joining(", "));
        Dimension existingDimensionCombination = dimensionRepository.findByName(dimensionName).orElse(null);

        //there are already saved facts for this ratio and dimension combination
        if (existingDimensionCombination != null) {
            String where = " WHERE ro.dimension_id =" + existingDimensionCombination.getId()
                    + filters_where.stream().collect(Collectors.joining(" ")) + " \nORDER BY ro.name";

            // this part uses substring_index function from mysql to split values in columns to show results on the frontend
            String substringQuery = "SELECT " + selectsWithSubstring.stream().collect(Collectors.joining(",")) + ", "
                    + ratiosResult
                    .stream()
                    .map(r -> "FORMAT(" + r.getName().toLowerCase().replaceAll(" ", "_") + ".value,2) " +
                            "as '" + r.getName().toLowerCase().replaceAll(" ", "_") + "' ")
                    .collect(Collectors.joining(",")) + from + ratiosJoins + where + limit;
            factsResult = factRepository.nativeQuery(substringQuery);

            if (factsResult.isEmpty()){
                executedQuery = "No facts found for this combination!";
                queryMethod = "";
            }
            else{
                executedQuery = substringQuery;
                queryMethod = "Returned existing facts.";
            }
        }
        else{
            executedQuery = "No facts found for this combination!";
            queryMethod = "";
        }

        // change IDs to names to display on fronted
        ratios.clear();
        ratiosResult.forEach(ratio -> ratios.add(ratio.getName()));
        dimensions.clear();
        dimensionsResult.forEach( dimension -> dimensions.add(dimension.getName()));

        return factsResult;
    }

    @Override
    public List<String[]> gdwhNcbQuery(List<String> ratios, List<String> dimensions, List<String> filters) {
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        dimensions.stream().distinct().forEach( d -> dimensionsResult.add(dimensionRepository.findById(Long.parseLong(d)).orElse(null)));

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

        List<Dimension> atomicLevels = new ArrayList<>();
        dCombinations.forEach(dc -> {
            dimensionRepository.findAtomicLevels(dc).forEach(atomicLevels::add);
        });

        List<String> filters_where = new ArrayList<>();
        if (filters != null) {
            Map<String, String> filtersMap = filters
                    .stream()
                    .distinct()
                    .map(s -> s.split("_"))
                    .collect(toMap(s -> s[0], s -> s[1]));

            filtersMap.values().stream().distinct().forEach( dimensionId -> {
                Dimension dimension = dimensionRepository.findById(Long.parseLong(dimensionId)).orElse(null);

                List<String> roIds = filtersMap
                        .entrySet()
                        .stream()
                        .filter(x -> dimensionId.equals(x.getValue()))
                        .map(x -> x.getKey())
                        .collect(Collectors.toList());

                filters_where.add(" AND _"+dimension.getName().replaceAll(" ","_").toLowerCase()+".id IN ("
                        +roIds.stream().collect(Collectors.joining(","))+")");

                if(!dimensionsResult.contains(dimension))
                    dimensionsResult.add(dimension);
            });
        }

        String query = "";
        List<String> selects = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        Deque<String> roJoins = new ArrayDeque<>();
        String where = "WHERE ro.dimension_id IN(" + dCombinations.stream().collect(Collectors.joining(",")) + ") " +
                filters_where.stream().collect(Collectors.joining(" "))+ "\n";
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();

        Deque<Dimension> dimensionHierarchy = new ArrayDeque<>();

        for (Dimension dimension : dimensionsResult) {

            if (atomicLevels.stream().filter(d -> dimension.equals(d)).findAny().orElse(null) != null && !dimensionHierarchy.contains(dimension))
                dimensionHierarchy.addFirst(dimension);

            else if (dimensionRepository.findChildByParent(dimension.getId()).orElse(null) != null ){

                Dimension child = dimensionRepository.findChildByParent(dimension.getId()).orElse(null);

                if(!dimensionHierarchy.contains(child))
                    dimensionHierarchy.addLast(child);
                while ( dimensionRepository.findChildByParent(child.getId()).orElse(null) != null ){
                    child = dimensionRepository.findChildByParent(child.getId()).orElse(null);
                    if (!dimensionHierarchy.contains(child))
                        dimensionHierarchy.addFirst(child);
                }
                if(!dimensionHierarchy.contains(dimension))
                    dimensionHierarchy.addLast(dimension);
            }
        }

        for (Dimension dh : dimensionHierarchy){
            if ( atomicLevels.stream().filter(d -> dh.equals(d)).findAny().orElse(null) != null ) {
                String atomicElement = dh.getName().replaceAll(" ","_").toLowerCase();
                roJoins.addFirst("INNER JOIN reference_object_combination _ro_"+atomicElement+" ON _ro_"+atomicElement+".combination_id = ro.id\n" +
                        "INNER JOIN reference_object _"+atomicElement+" ON _"+atomicElement+".id = _ro_"
                        +atomicElement+".subordinate_id AND _"+atomicElement+".dimension_id = "+ dh.getId() +" \n");
            }
            else {
                Dimension child1 = dimensionRepository.findChildByParent(dh.getId()).orElse(null);
                if (child1!= null){
                    String childQuery = child1.getName().replaceAll(" ", "_").toLowerCase();
                    String parentQuery = dh.getName().replaceAll(" ", "_").toLowerCase();
                    roJoins.add("INNER JOIN reference_object_hierarchy _" + childQuery + "_" + parentQuery + " ON _"
                            + childQuery + "_" + parentQuery + ".child_id = _" + childQuery + ".id\n"
                            + "INNER JOIN reference_object _" + parentQuery + " ON _" + parentQuery + ".id = _"
                            + childQuery + "_" + parentQuery + ".parent_id AND _" + parentQuery + ".dimension_id = " + dh.getId() + " \n");
                }
            }
        }

        List<Dimension> dimensions_sorted = dimensionsResult.stream().sorted(Comparator.comparingLong(Dimension::getId)).collect(Collectors.toList());
        for (Dimension dimension : dimensions_sorted){
            String dimensionQuery = dimension.getName().replaceAll(" ","_").toLowerCase();
            groupBy.add("_"+dimensionQuery+".id");
            orderBy.add("_"+dimensionQuery+".name");
            selects.add("_"+dimensionQuery+".name AS '"+dimensionQuery+"'");
        }

        for (Ratio ratio : ratiosResult) {
            String ratioQuery = ratio.getName().toLowerCase().replaceAll(" ", "_");
            roJoins.addLast("INNER JOIN fact " + ratioQuery + " ON "
                    + ratioQuery + ".reference_object_id = ro.id AND " + ratioQuery + ".ratio_id = " + ratio.getId() + "\n");
        }

        query = "SELECT " + selects.stream().collect(Collectors.joining(",")) + ", "
                + ratiosResult.stream()
                .map(r-> "FORMAT(sum("+ r.getName().toLowerCase().replaceAll(" ","_") +".value),2)")
                .collect(Collectors.joining(","))
                + from + roJoins.stream().collect(Collectors.joining(" ")) + where
                + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                + "\nORDER BY " + orderBy.stream().collect(Collectors.joining(","))
                + limit;

        // change IDs to names to display on fronted
        ratios.clear();
        ratiosResult.forEach(ratio -> ratios.add(ratio.getName()));
        dimensions.clear();
        dimensions_sorted.forEach( dimension -> dimensions.add(dimension.getName()));

        executedQuery = query;
        queryMethod="";

        return factRepository.nativeQuery(query);
    }

    public boolean isEmptyFactsForDimensionCombinationAndRatio(Long ratioId, Long dimensionId){

        String query = "SELECT f.value FROM fact f \n" +
                "WHERE f.ratio_id = "+ratioId+" and f.reference_object_id =\n" +
                "(SELECT id FROM reference_object\n" +
                "WHERE dimension_id = "+dimensionId+" LIMIT 1)\n" +
                "LIMIT 1 ";

        return factRepository.nativeQuery(query).isEmpty();
    }

    @Override
    public List<String> getAllDimensionCombinations() throws FileNotFoundException {
        //set the dimension combination ID with the atomic levels which contains the ref objs and facts stored
        //it's max(id) from dimension on NCB DB
        String dimensionCombinationWithAtomicLevels = "20";
        List<String> insertDC_SQL_Queries = new ArrayList<>();
        List<String> selectRO_SQL_Queries = new ArrayList<>();

        List<String> dimensionCombinationIds = new ArrayList<>();

        //query to return all hierarchy levels which can be stored as reference object
        List<String> dimensionsIds = new ArrayList<>();
        dimensionRepository.findAtomicAndHierarchyLevels().forEach(dimension -> dimensionsIds.add(dimension.getId().toString()));

        //generates all combinations of hierarchy levels and add to result
/*
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
*/

        Generator.subset(dimensionsIds)
                .simple()
                .stream()
                .forEach(r -> dimensionCombinationIds.add(String.valueOf(r)));

        //first element is always empty, delete it
        dimensionCombinationIds.remove(0);

        for (String dcId : dimensionCombinationIds){
            if (dcId.contains(",")){
                List<String> groupBy = new ArrayList<>();
                List<String> orderBy = new ArrayList<>();
                List<Dimension> dimensionsResult = new ArrayList<>();
                List<Dimension> atomicLevels = new ArrayList<>();
                Deque<String> roJoins = new ArrayDeque<>();
                Deque<Dimension> dimensionHierarchy = new ArrayDeque<>();
                dimensionRepository.findAtomicLevels(dimensionCombinationWithAtomicLevels).forEach(atomicLevels::add);
                dcId = dcId.replace("[","");
                dcId = dcId.replace("]","");
                List<String> dimensionIds = Stream.of(dcId.split("\\s*,\\s*")).collect(Collectors.toList());

                dimensionIds.stream().forEach(dId -> dimensionsResult.add(dimensionRepository.findById(Long.parseLong(dId)).orElse(null)));

                for (Dimension dimension : dimensionsResult) {

                    if (atomicLevels.stream().filter(d -> dimension.equals(d)).findAny().orElse(null) != null && !dimensionHierarchy.contains(dimension))
                        dimensionHierarchy.addFirst(dimension);

                    else if (dimensionRepository.findChildByParent(dimension.getId()).orElse(null) != null ){

                        Dimension child = dimensionRepository.findChildByParent(dimension.getId()).orElse(null);

                        if(!dimensionHierarchy.contains(child))
                            dimensionHierarchy.addLast(child);
                        while ( dimensionRepository.findChildByParent(child.getId()).orElse(null) != null ){
                            child = dimensionRepository.findChildByParent(child.getId()).orElse(null);
                            if (!dimensionHierarchy.contains(child))
                                dimensionHierarchy.addFirst(child);
                        }
                        if(!dimensionHierarchy.contains(dimension))
                            dimensionHierarchy.addLast(dimension);
                    }
                }

                for (Dimension dh : dimensionHierarchy){
                    if ( atomicLevels.stream().filter(d -> dh.equals(d)).findAny().orElse(null) != null ) {
                        String atomicElement = dh.getName().replaceAll(" ","_").toLowerCase();
                        roJoins.addFirst(" INNER JOIN reference_object_combination _ro_"+atomicElement+" ON _ro_"+atomicElement+".combination_id = ro.id INNER JOIN reference_object _"+atomicElement
                                +" ON _"+atomicElement+".id = _ro_"+atomicElement+".subordinate_id AND _"+atomicElement+".dimension_id = "+dh.getId());
                    }
                    else {
                        Dimension child1 = dimensionRepository.findChildByParent(dh.getId()).orElse(null);
                        if (child1!= null){
                            String childQuery = child1.getName().replaceAll(" ", "_").toLowerCase();
                            String parentQuery = dh.getName().replaceAll(" ", "_").toLowerCase();
                            roJoins.add(" INNER JOIN reference_object_hierarchy _" + childQuery + "_" + parentQuery + " ON _" + childQuery + "_" + parentQuery + ".child_id = _" + childQuery + ".id"
                                    + " INNER JOIN reference_object _" + parentQuery + " ON _" + parentQuery + ".id = _"
                                    + childQuery + "_" + parentQuery + ".parent_id AND _" + parentQuery + ".dimension_id = " + dh.getId());
                        }
                    }
                }

                String newDimensionCombination = dimensionsResult.stream().map(d -> d.getName()).collect(Collectors.joining(", "));
                String insert_new_dimension = "insert into dimension (name, is_time) values (\""+ newDimensionCombination +"\", false);";
                insertDC_SQL_Queries.add(insert_new_dimension);

                for (Dimension dimension : dimensionsResult){
                    String dimensionQuery = dimension.getName().replaceAll(" ","_").toLowerCase();
                    groupBy.add("_"+dimensionQuery+".id");
                    orderBy.add("_"+dimensionQuery+".name");
                    String insertDimensionCombination = "insert into dimension_combination (combination_id, subordinate_id, show_on) values (" +
                            "(select id from dimension where name = \""+ newDimensionCombination +"\")," +
                            "(select id from dimension where name = \""+ dimension.getName() +"\"), false);";
                    insertDC_SQL_Queries.add(insertDimensionCombination);
                }

                String select = "SELECT CONCAT_WS(\", \", "+orderBy.stream().collect(Collectors.joining(","))+") as 'roName', "
                        + "(SELECT id FROM dimension WHERE NAME = \""+newDimensionCombination+"\") AS 'dimension_id', "
                        + "(SELECT is_time FROM DIMENSION WHERE NAME = \""+newDimensionCombination+"\") AS 'is_time'," +
                        "ROUND(SUM(REVENUE.VALUE),2) AS 'REVENUE', ROUND(SUM(PRODUCT_COST.VALUE),2) AS 'PRODUCT_COST', ROUND(SUM(PROFIT.VALUE),2) AS 'PROFIT', ROUND(SUM(SELL_PRICE.VALUE),2) AS 'SELL_PRICE', ROUND(SUM(PURCHASE_AMOUNT.VALUE),2) 'PURCHASE_AMOUNT' FROM reference_object ro ";
                String where = " WHERE ro.dimension_id IN(20)";
                String facts = " INNER JOIN fact revenue ON revenue.reference_object_id = ro.id AND revenue.ratio_id = 1 INNER JOIN fact product_cost ON product_cost.reference_object_id = ro.id AND product_cost.ratio_id = 2 INNER JOIN fact profit ON profit.reference_object_id = ro.id AND profit.ratio_id = 3 INNER JOIN fact sell_price ON sell_price.reference_object_id = ro.id AND sell_price.ratio_id = 4 INNER JOIN fact purchase_amount ON purchase_amount.reference_object_id = ro.id AND purchase_amount.ratio_id = 5 ";
                String query = select + roJoins.stream().collect(Collectors.joining(" ")) + facts + where
                        + " GROUP BY " + groupBy.stream().collect(Collectors.joining(", "))
                        + " ORDER BY " + orderBy.stream().collect(Collectors.joining(", ")) +";";
                selectRO_SQL_Queries.add(query);
            }
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream("c:/temp/acb_insertDCQueries.sql"));
        for (String q : insertDC_SQL_Queries)
            pw.println(q);
        pw.close();
        //delete lines 18509-18514 that corresponds to atomic levels already saved

        PrintWriter pw2 = new PrintWriter(new FileOutputStream("c:/temp/acb_RO_Queries.sql"));
        for (String q : selectRO_SQL_Queries)
            pw2.println(q);
        pw2.close();

        return dimensionCombinationIds;
    }

    @Override
    public List<String[]> adHocQuery(String query) {
        executedQuery = query;
        return factRepository.nativeQuery(query);
    }

}
