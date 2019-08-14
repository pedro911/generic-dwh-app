package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import de.wwu.ercis.genericdwhapp.services.genericdwh.FactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
