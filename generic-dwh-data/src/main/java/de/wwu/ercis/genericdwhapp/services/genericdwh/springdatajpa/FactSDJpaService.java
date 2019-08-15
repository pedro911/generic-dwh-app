package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
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
    private final ReferenceObjectCombinationRepository referenceObjectCombinationRepository;
    private final DimensionHierarchyRepository dimensionHierarchyRepository;
    private final DimensionHierarchyService dimensionHierarchyService;
    private final DimensionCombinationRepository dimensionCombinationRepository;

    private String queryMethod ="";
    private String executedQuery ="";

    public FactSDJpaService(FactRepository factRepository, RatioRepository ratioRepository,
                            DimensionRepository dimensionRepository, ReferenceObjectRepository referenceObjectRepository,
                            ReferenceObjectCombinationRepository referenceObjectCombinationRepository,
                            DimensionHierarchyRepository dimensionHierarchyRepository,
                            DimensionHierarchyService dimensionHierarchyService,
                            DimensionCombinationRepository dimensionCombinationRepository) {
        this.factRepository = factRepository;
        this.ratioRepository = ratioRepository;
        this.dimensionRepository = dimensionRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.referenceObjectCombinationRepository = referenceObjectCombinationRepository;
        this.dimensionHierarchyRepository = dimensionHierarchyRepository;
        this.dimensionHierarchyService = dimensionHierarchyService;
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
    public List<Fact> stdQuery(List<String> ratios, List<String> dimensions, List<String> dCombinations) {

        queryMethod = "Only Reading.";

        List<Fact> factsResult = new ArrayList<Fact>();

        for (String dimension_root : dCombinations) {
            for (String radio_id : ratios) {
                for (String dimension_id : dimensions) {
                    this.genericDWHResults(dimension_id, radio_id, dimension_root).forEach(factsResult::add);
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
    public List<Fact> findByDimensionIdAndRatioId(String dimensionId, String ratioId) {
        return factRepository.findByDimensionIdAndRatioId(dimensionId, ratioId);
    }

    @Override
    public List<Fact> genericDWHResults(String dimensionId, String ratioId, String dimensionCombination) {
        return factRepository.genericDWHResults(dimensionId, ratioId, dimensionCombination);
    }

    @Override
    public List<Object[]> gdwhDynQuery(List<String> ratios, List<String> dimensions) {
        //TODO
        List<Object[]> factsResult = new ArrayList<>();
        List<String> dCombinations = new ArrayList<>();
        //split ratios and dimension combinations
        Map<String,String> dCombinationsMap = ratios
                .stream()
                .distinct()
                .map(s -> s.split("_"))
                .collect(toMap(s -> s[0], s -> s[1]));
        ratios.clear();
        //add unique dimension combinations and all ratios to lists
        dCombinationsMap.forEach( (k,v) -> {
            if(!dCombinations.contains(v))
                dCombinations.add(v);
            ratios.add(k);
        });

        DimensionCombination dimensionCombination = new DimensionCombination();

        String query = "";
        List<String> selects = new ArrayList<>();
        String from = " FROM reference_object ro \n";
        String joins = "";
        String where = "";
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();

        queryMethod="";
        Dimension dimensionResult = new Dimension();
        Ratio ratioResult;
        //List<Fact> factsResult = new ArrayList<Fact>();
        Iterable<Long> ratioIDs = ratios.stream().map(Long::valueOf).collect(Collectors.toList());
        List<Ratio> ratiosResult = ratioRepository.findAllById(ratioIDs);
        ratiosResult.forEach(System.out::println);

        // uses SQL custom query
        for (String dimension_root : dCombinations) {
            System.out.println(dimension_root);
            List<Ratio> ratioList = ratioRepository.findByDimensionCombinationId(Long.parseLong(dimension_root));
            where = " WHERE ro.dimension_id IN("+dCombinations.stream().collect(Collectors.joining(","))+")\n";
            for (String radio_id : ratios) {
                ratioResult = ratioRepository.findById((Long.parseLong(radio_id))).orElse(null);
                String ratioQuery = ratioResult.getName().toLowerCase().replaceAll(" ","_");
                if(!joins.contains("fact"))
                    joins = joins + "INNER JOIN fact " + ratioQuery +" ON "
                            + ratioQuery+".reference_object_id = ro.id AND "+ratioQuery+".ratio_id="+ratioResult.getId();
                if (ratioList.contains(ratioResult)){
                    for (String dimension_id : dimensions) {
                        dimensionResult = dimensionRepository.findById(Long.parseLong(dimension_id)).orElse(null);
                        String dimensionQuery = dimensionResult.getName().replaceAll(" ","_").toLowerCase();
                        groupBy.add("_"+dimensionQuery+".id");
                        orderBy.add("_"+dimensionQuery+".name");
                        selects.add("_"+dimensionQuery+".name");
                        joins = joins +
                                " INNER JOIN reference_object_combination _ro_"+dimensionQuery+" ON _ro_"+dimensionQuery+".combination_id = ro.id\n" +
                                " INNER JOIN reference_object _"+dimensionQuery+" ON _"+dimensionQuery+".id = _ro_"
                                +dimensionQuery+".subordinate_id AND _"+dimensionQuery+".dimension_id = "+dimensionResult.getId() +" \n";
                        ReferenceObject roResult = referenceObjectRepository.findFirstByDimension(dimensionResult).orElse(null);
                        if (null == this.findByReferenceObjectAndRatio(roResult,ratioResult)  ) {
                            //this.genericDWHResults(dimension_id, radio_id, dimension_root).forEach(factsResult::add);

                            //factsResult.forEach(fact -> factRepository.insertNewFact(fact.getReferenceObjectId(),fact.getRatioId(),fact.getValue()));
                            if (queryMethod.startsWith("R") && !queryMethod.contains("and"))
                                queryMethod = queryMethod + " and New Facts inserted";
                            else queryMethod = "New Facts inserted";
                        }
                        else {
                            //this.findByDimensionIdAndRatioId(dimension_id, radio_id).forEach(factsResult::add);
                            executedQuery = "oi";
                            if (queryMethod.startsWith("N") && !queryMethod.contains("and"))
                                queryMethod = queryMethod + " and Returned Existing Facts";
                            else queryMethod = "Returned Existing Facts";
                        }
                    }
                }
            }
        }

        query = "SELECT " + selects.stream().collect(Collectors.joining(",")) + ", "
                + ratiosResult.stream().map(r-> "FORMAT(sum("+ r.getName().toLowerCase() +".value),2)").collect(Collectors.joining(","))
                + from + joins + where
                + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                + "\n ORDER BY " + orderBy.stream().collect(Collectors.joining(","));

        System.out.println(query);
        factRepository.nativeQuery(query);
        executedQuery = query;
        return factsResult;
    }

    @Override
    public List<Object[]> gdwhStdQuery(List<String> ratios, List<String> dimensions) {
        List<String> dCombinations = new ArrayList<>();
        List<Ratio> ratiosResult = new ArrayList<>();
        List<Dimension> dimensionsResult = new ArrayList<>();
        //split ratios and dimension combinations
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
        dimensions.forEach( d -> dimensionsResult.add(dimensionRepository.findById(Long.parseLong(d)).orElse(null)));
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

    @Override
    public String query() {
        return executedQuery;
    }

}
