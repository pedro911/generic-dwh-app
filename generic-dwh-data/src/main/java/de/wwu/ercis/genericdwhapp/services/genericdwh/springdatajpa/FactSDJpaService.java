package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import de.wwu.ercis.genericdwhapp.services.genericdwh.FactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String queryMethod ="";

    public FactSDJpaService(FactRepository factRepository, RatioRepository ratioRepository,
                            DimensionRepository dimensionRepository, ReferenceObjectRepository referenceObjectRepository,
                            ReferenceObjectCombinationRepository referenceObjectCombinationRepository,
                            DimensionHierarchyRepository dimensionHierarchyRepository,
                            DimensionHierarchyService dimensionHierarchyService) {
        this.factRepository = factRepository;
        this.ratioRepository = ratioRepository;
        this.dimensionRepository = dimensionRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.referenceObjectCombinationRepository = referenceObjectCombinationRepository;
        this.dimensionHierarchyRepository = dimensionHierarchyRepository;
        this.dimensionHierarchyService = dimensionHierarchyService;
    }

    @Override
    public Set<Fact> findAll() {
        Set<Fact> facts = new HashSet<>();
        factRepository.findAll().forEach(facts::add);
        return facts;
    }

    @Override
    public Fact findById(Long aLong) {
        return factRepository.findById(aLong).orElse(null);
    }

    @Override
    public Fact save(Fact object) { return factRepository.save(object);}

    @Override
    public void delete(Fact object) {
        factRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        factRepository.deleteById(aLong);
    }

    @Override
    public Fact findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio) {
        return factRepository.findByReferenceObjectAndRatio(referenceObject, ratio).orElse(null);
    }

    @Override
    public List<Fact> dynETLQuery(List<String> ratios, List<String> dimensions, List<String> dCombinations) {

        queryMethod="";
        Dimension dimensionResult = new Dimension();
        Ratio ratioResult = new Ratio();
        List<Fact> factsResult = new ArrayList<Fact>();

        // uses SQL custom query
        for (String dimension_root : dCombinations) {
            for (String radio_id : ratios) {
                ratioResult.setId(Long.parseLong(radio_id));
                for (String dimension_id : dimensions) {
                    dimensionResult.setId(Long.parseLong(dimension_id));
                    ReferenceObject roResult = referenceObjectRepository.findFirstByDimension(dimensionResult).orElse(null);
                    if (null == this.findByReferenceObjectAndRatio(roResult,ratioResult)) {
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

        if (dimensions.size()>1){
            log.debug("dimensions size:" +dimensions.size());
            for(int i=0;i<dimensions.size();i++){
                List<DimensionRoot> dimensionRoots = dimensionHierarchyService.findAllByParentId(Long.parseLong(dimensions.get(i)));
                for (String ratio_id : ratios){
                    for (DimensionRoot dr : dimensionRoots){
                        if (dr.getParentId() == "null"){
                            System.out.println(dr.getName());
                            System.out.println("root");
                        }
                        else System.out.println("child");
                    }
                    System.out.println(dimensions.get(i));
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

}
