package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
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
    public Fact save(Fact object) {
        return factRepository.save(object);
    }

    @Override
    public void delete(Fact object) {
        factRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        factRepository.deleteById(aLong);
    }

    @Override
    public List<Fact> findByOrderByRatioIdAsc() {
        return factRepository.findByOrderByRatioIdAsc();
    }

    @Override
    public Fact findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio) {
        return factRepository.findByReferenceObjectAndRatio(referenceObject, ratio).orElse(null);
    }

    @Override
    public Fact findByReferenceObjectIdAndRatioId(Long roId, Long ratioId) {
        return factRepository.findByReferenceObjectIdAndRatioId(roId, ratioId).orElse(null);
    }

    @Override
    public Fact findByReferenceObjectId(Long id) {
        return factRepository.findByReferenceObjectId(id).orElse(null);
    }

    @Override
    public Fact findFirstByReferenceObjectIdAndRatioId(Long roId, Long ratioId) {
        return factRepository.findFirstByReferenceObjectIdAndRatioId(roId, ratioId).orElse(null);
    }

    @Override
    public List<Fact> queryResults(List<String> ratios, List<String> dimensions, List<String> dCombinations) {

        queryMethod="";
        Dimension dimension = new Dimension();
        Ratio ratio = new Ratio();
        Double value_sum = Double.valueOf(0);
        List<Fact> factsResult = new ArrayList<Fact>();

        // uses referenceObjectCombination
        for (String dimension_root : dCombinations) {
            for (String radio_id : ratios) {
                ratio = ratioRepository.findById(Long.parseLong(radio_id)).orElse(null);
                for (String dimension_id : dimensions) {
                    dimension = dimensionRepository.findById(Long.parseLong(dimension_id)).orElse(null);
                    List<ReferenceObject> referenceObjectsResults = referenceObjectRepository.findAllByDimensionIn(dimension);
                    if (null == this.findFirstByReferenceObjectIdAndRatioId(referenceObjectsResults.get(0).getId(),Long.parseLong(radio_id))) {
                        for (ReferenceObject referenceObjectResult : referenceObjectsResults) {
                            Fact resultFact = new Fact();
                            List<ReferenceObjectCombination> referenceObjectsFactValues =
                                    referenceObjectCombinationRepository.findAllBySubordinateId(referenceObjectResult.getId());
                            for (ReferenceObjectCombination ro2 : referenceObjectsFactValues) {
                                Fact tempFact = this.findByReferenceObjectIdAndRatioId(ro2.getCombinationId(), ratio.getId());
                                value_sum = value_sum + tempFact.getValue();
                            }
                            resultFact.setReferenceObject(referenceObjectResult);
                            resultFact.setRatio(ratio);
                            resultFact.setReferenceObjectId(referenceObjectResult.getId());
                            resultFact.setRatioId(Long.parseLong(radio_id));
                            resultFact.setValue(value_sum);
                            factsResult.add(resultFact);
                            this.save(resultFact);
                            value_sum = Double.valueOf(0);
                        }
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

}
