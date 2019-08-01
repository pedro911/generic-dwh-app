package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.*;
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

    private String queryMethod ="";

    public FactSDJpaService(FactRepository factRepository, RatioRepository ratioRepository,
                            DimensionRepository dimensionRepository, ReferenceObjectRepository referenceObjectRepository,
                            ReferenceObjectCombinationRepository referenceObjectCombinationRepository) {
        this.factRepository = factRepository;
        this.ratioRepository = ratioRepository;
        this.dimensionRepository = dimensionRepository;
        this.referenceObjectRepository = referenceObjectRepository;
        this.referenceObjectCombinationRepository = referenceObjectCombinationRepository;
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
    public List<Fact> queryResults(List<String> ratios, List<String> dimensions, List<String> dCombinations) {

        queryMethod="";
        Dimension dimension = new Dimension();
        List<Fact> factsResult = new ArrayList<Fact>();

        // uses SQL custom query
        for (String dimension_root : dCombinations) {
            for (String radio_id : ratios) {
                Ratio ratioResult = ratioRepository.findById(Long.parseLong(radio_id)).orElse(null);
                for (String dimension_id : dimensions) {
                    dimension = dimensionRepository.findById(Long.parseLong(dimension_id)).orElse(null);
                    //maybe find one or first, not all, but if it has filters, need to scan ALL ro_results to check which one(s) need to be inserted!
                    //List<ReferenceObject> referenceObjectsResults = referenceObjectRepository.findAllByDimensionIn(dimension);
                    ReferenceObject roResult = referenceObjectRepository.findFirstByDimension(dimension).orElse(null);
                    if (null == this.findByReferenceObjectAndRatio(roResult,ratioResult)) {
                        this.genericDWHResults(dimension_id, radio_id, dimension_root).forEach(factsResult::add);
                        factsResult.forEach(fact -> factRepository.insertNewFact(fact.getReferenceObjectId(),fact.getRatioId(),fact.getValue()));
                        if (queryMethod.startsWith("R") && !queryMethod.contains("and"))
                            queryMethod = queryMethod + " and New Facts inserted";
                        else queryMethod = "New Facts inserted";
                    }
                    else {
                        findByDimensionIdAndRatioId(dimension_id, radio_id).forEach(factsResult::add);
                        if (queryMethod.startsWith("N") && !queryMethod.contains("and"))
                            queryMethod = queryMethod + " and Returned Existing Facts";
                        else queryMethod = "Returned Existing Facts";
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
    public List<Fact> findSpecial() {
        String query =
                "SELECT _ro_result.subordinate_id, _fact.ratio_id, SUM(_fact.value)\n" +
                        "FROM reference_object ro\n" +
                        "INNER JOIN reference_object_combination _ro_result ON _ro_result.combination_id = ro.id\n" +
                        "INNER JOIN reference_object _result ON _result.id = _ro_result.subordinate_id AND _result.dimension_id = 2\n" +
                        "INNER JOIN fact _fact ON _fact.reference_object_id = ro.id AND _fact.ratio_id = 2\n" +
                        "WHERE ro.dimension_id IN (14)\n" +
                        "GROUP BY _result.id";

        return factRepository.findSpecial(query);
    }

}
