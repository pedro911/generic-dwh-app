package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioRoot;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionCombinationRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.RatioRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.RatioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RatioSDJpaService implements RatioService {

    private final RatioRepository ratioRepository;
    private final DimensionCombinationRepository dimensionCombinationRepository;

    public RatioSDJpaService(RatioRepository ratioRepository, DimensionCombinationRepository dimensionCombinationRepository) {
        this.ratioRepository = ratioRepository;
        this.dimensionCombinationRepository = dimensionCombinationRepository;
    }

    @Override
    public List<Ratio> findAll() {
        List<Ratio> ratios = new ArrayList<>();
        ratioRepository.findAll().forEach(ratios::add);
        return ratios;
    }

    @Override
    public List<Ratio> findAllSort(Sort sort) {
        List<Ratio> ratios = new ArrayList<>();
        ratioRepository.findAll(sort).forEach(ratios::add);
        return ratios;
    }

    @Override
    public Ratio findById(Long aLong) {
        return ratioRepository.findById(aLong).orElse(null);
    }

    @Override
    public Ratio save(Ratio object) {
        return ratioRepository.save(object);
    }

    @Override
    public List<Ratio> findByOrderByIdAsc() {
        return ratioRepository.findByOrderByIdAsc();
    }

    @Override
    public List<Ratio> findByDimensionCombinationId(Long dimensionId) {
        return ratioRepository.findByDimensionCombinationId(dimensionId);
    }

    @Override
    public List<RatioRoot> findAllByRoot() {
        // first get distinct dimension combinations from dimension_combination
        // then find ratios for each dimension_combination_id
        // then create a hierarchy, where parent = dimension_combination_id and children = ratios related to that parent

        // list of distinct dimension combinations
        List<Long> dimensionCombinations = dimensionCombinationRepository.findDimensionsByCombinationId();

        // Arrange - String corresponds to the Id
        Map<Long, RatioRoot> ratioRoots = new HashMap<>();

        // populate a Map for each dimension combination find their related ratios
        for(Long dimensionCombinationId: dimensionCombinations){
            List<Ratio> ratios = findByDimensionCombinationId(dimensionCombinationId);
            RatioRoot ratioParent = new RatioRoot();
            ratioParent.setId(dimensionCombinationId.toString());
            ratioParent.setParentId("null");
            ratioRoots.put(dimensionCombinationId,ratioParent);

            for (Ratio r: ratios){
                RatioRoot ratioChild = new RatioRoot();
                ratioChild.setId(r.getId().toString());
                ratioChild.setParentId(r.toString());
                ratioChild.setName(r.getName());
                ratioRoots.put(r.getId(),ratioChild);
                ratioParent.addChildrenItem(ratioChild);
            }
        }

        // Get/add the root
        List<RatioRoot> ratioRootsResult = new ArrayList<RatioRoot>();
        for(RatioRoot rr : ratioRoots.values()){
            if(rr.getParentId().equals("null"))
                ratioRootsResult.add(rr);
        }
        // Print
/*        for(RatioRoot r: ratioRootsResult){
            System.out.println("ratioRootsResult contains "+ratioRootsResult.size()+" that are : "+ r);
        }*/
        return ratioRootsResult;
    }
}
