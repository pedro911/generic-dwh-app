package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionRoot;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionCombinationRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionHierarchyRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DimensionHierarchySDJpaService implements DimensionHierarchyService {

    private final DimensionHierarchyRepository dimensionHierarchyRepository;
    private final DimensionRepository dimensionRepository;
    private final DimensionCombinationRepository dimensionCombinationRepository;

    public DimensionHierarchySDJpaService(DimensionHierarchyRepository dimensionHierarchyRepository,
                                          DimensionRepository dimensionRepository,
                                          DimensionCombinationRepository dimensionCombinationRepository) {
        this.dimensionHierarchyRepository = dimensionHierarchyRepository;
        this.dimensionRepository = dimensionRepository;
        this.dimensionCombinationRepository = dimensionCombinationRepository;
    }

    @Override
    public List<DimensionHierarchy> findAll() {
        List<DimensionHierarchy> dimensionHierarchies = new ArrayList<>();
        dimensionHierarchyRepository.findAll().forEach(dimensionHierarchies::add);
        return dimensionHierarchies;
    }

    @Override
    public List<DimensionHierarchy> findAllSort(Sort sort) {
        List<DimensionHierarchy> dimensionHierarchies = new ArrayList<>();
        dimensionHierarchyRepository.findAll(sort).forEach(dimensionHierarchies::add);
        return dimensionHierarchies;
    }

    @Override
    public DimensionHierarchy findById(Long aLong) {
        return dimensionHierarchyRepository.findById(aLong).orElse(null);
    }

    @Override
    public DimensionHierarchy save(DimensionHierarchy object) {
        return dimensionHierarchyRepository.save(object);
    }

    @Override
    public List<DimensionHierarchy> findByOrderByParentIdAsc() {
        return dimensionHierarchyRepository.findByOrderByParentIdAsc();
    }

    @Override
    public List<DimensionRoot> findAllByRoot() {
        // first get distinct dimension roots from dimension table
        // then find atomic levels for each dimension_id
        // then create a hierarchy, where parent = dimension_id and children = dimension levels related to that parent

        // list of distinct dimension roots
        List<Dimension> dimensionRoots = dimensionRepository.findAllRoots();

        // Arrange - String corresponds to the Id
        Map<Long, DimensionRoot> dimensionRootsMap = new HashMap<>();

        List<Dimension> atomicLevels = new ArrayList<>();
        List<Long> dimensionCombinationsIds = dimensionCombinationRepository.findDimensionsByCombinationId();

        for (Long dcId: dimensionCombinationsIds){
            dimensionRepository.findAtomicLevels(dcId.toString()).forEach(atomicLevels::add);

            for (Dimension dimension: dimensionRoots){
                Deque<Dimension> dimensionChildrenDeque = new ArrayDeque<>();
                DimensionRoot dimensionParent = new DimensionRoot();
                dimensionParent.setId(dimension.getId().toString());
                dimensionParent.setName(dimension.getName());
                dimensionParent.setParentId("null");
                dimensionRootsMap.put(dimension.getId(),dimensionParent);

                List<Dimension> dimensionsChildren = dimensionRepository.findChildByParentList(dimension.getId());

                for (Dimension dc : dimensionsChildren) {

                    if(!dimensionChildrenDeque.contains(dc))
                        dimensionChildrenDeque.addFirst(dc);

                    Dimension child = dimensionRepository.findChildByParent(dc.getId()).orElse(null);

                    if (child != null ){
                        Dimension child2 = child;
                        if(!dimensionChildrenDeque.contains(child))
                            dimensionChildrenDeque.addLast(child);

                        if (atomicLevels.stream().filter(d -> d.equals(child2)).findAny().orElse(null) != null && !dimensionChildrenDeque.contains(child2))
                            dimensionChildrenDeque.addLast(child);


                        while ( dimensionRepository.findChildByParent(child.getId()).orElse(null) != null ){
                            child = dimensionRepository.findChildByParent(child.getId()).orElse(null);
                            Dimension child3 = child;

                            if (!dimensionChildrenDeque.contains(child))
                                dimensionChildrenDeque.addLast(child);
                            if (atomicLevels.stream().filter(d -> d.equals(child3)).findAny().orElse(null) != null && !dimensionChildrenDeque.contains(child3))
                                dimensionChildrenDeque.addLast(child);
                        }
                    }
                }

                for (Dimension dc: dimensionChildrenDeque){
                    DimensionRoot dimensionChild = new DimensionRoot();
                    dimensionChild.setId(dc.getId().toString());
                    dimensionChild.setParentId(dimensionParent.getId());
                    dimensionChild.setName(dc.getName());
                    dimensionRootsMap.put(dc.getId(),dimensionChild);
                    dimensionParent.addChildrenItem(dimensionChild);
                }
            }
        }

        // Get/add the root
        List<DimensionRoot> dimensionRootsResult = new ArrayList<DimensionRoot>();
        for(DimensionRoot dr : dimensionRootsMap.values()){
            if(dr.getParentId().equals("null"))
                dimensionRootsResult.add(dr);
        }
        // Print
        /*
        for(DimensionRoot dr: dimensionRootsResult){
            System.out.println("ratioRootsResult contains "+dimensionRootsResult.size()+" that are : "+ dr);
        }
        */
        return dimensionRootsResult;
    }

}
