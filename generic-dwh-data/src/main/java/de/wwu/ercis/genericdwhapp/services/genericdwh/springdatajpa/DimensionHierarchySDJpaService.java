package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionRoot;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionHierarchyRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DimensionHierarchySDJpaService implements DimensionHierarchyService {

    private final DimensionHierarchyRepository dimensionHierarchyRepository;
    private final DimensionRepository dimensionRepository;

    public DimensionHierarchySDJpaService(DimensionHierarchyRepository dimensionHierarchyRepository, DimensionRepository dimensionRepository) {
        this.dimensionHierarchyRepository = dimensionHierarchyRepository;
        this.dimensionRepository = dimensionRepository;
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

        // input
        List<DimensionHierarchy> dimensionHierarchies = this.findByOrderByParentIdAsc();

        // Arrange - String corresponds to the Id
        Map<Long, DimensionRoot> dimensionRoots = new HashMap<>();

        // populate a Map
        for(DimensionHierarchy dh:dimensionHierarchies){
            //  ----- Child -----
            DimensionRoot dimensionChild;
            if(dimensionRoots.containsKey(dh.getChildId())){
                dimensionChild = dimensionRoots.get(dh.getChildId());
            }
            else{
                dimensionChild = new DimensionRoot();
                dimensionRoots.put(dh.getChildId(),dimensionChild);
            }
            dimensionChild.setId(dh.getChildId().toString());
            dimensionChild.setParentId(dh.getParent().toString());
            dimensionChild.setName(dh.getChild().getName());
            // no need to set ChildrenItems list because the constructor created a new empty list

            // ------ Parent ----
            DimensionRoot dimensionParent ;
            if(dimensionRoots.containsKey(dh.getParentId())){
                dimensionParent = dimensionRoots.get(dh.getParentId());
            }
            else{
                dimensionParent = new DimensionRoot();
                dimensionRoots.put(dh.getParentId(),dimensionParent);
            }
            dimensionParent.setId(dh.getParentId().toString());
            dimensionParent.setParentId("null");
            dimensionParent.setName(dh.getParent().getName());
            dimensionParent.addChildrenItem(dimensionChild);
        }

        // Get the root
        List<DimensionRoot> dimensionRootsResult = new ArrayList<DimensionRoot>();
        for(DimensionRoot dr : dimensionRoots.values()){
            if(dr.getParentId().equals("null"))
                dimensionRootsResult.add(dr);
        }
        // Print
/*        for(DimensionRoot dr: dimensionRootsResult){
            System.out.println("dimensionRootsResult contains "+dimensionRootsResult.size()+" that are : "+ dr);
        }*/
        return dimensionRootsResult;
    }

    @Override
    public List<DimensionRoot> findAllByParentId(Long id) {
        // input
        List<DimensionHierarchy> dimensionHierarchies = dimensionHierarchyRepository.findAllByParentId(id);

        // Arrange - String corresponds to the Id
        Map<Long, DimensionRoot> dimensionRoots = new HashMap<>();

        // populate a Map
        for(DimensionHierarchy dh:dimensionHierarchies){
            //  ----- Child -----
            DimensionRoot dimensionChild;
            if(dimensionRoots.containsKey(dh.getChildId())){
                dimensionChild = dimensionRoots.get(dh.getChildId());
            }
            else{
                dimensionChild = new DimensionRoot();
                dimensionRoots.put(dh.getChildId(),dimensionChild);
            }
            dimensionChild.setId(dh.getChildId().toString());
            dimensionChild.setParentId(dh.getParent().toString());
            dimensionChild.setName(dh.getChild().getName());
            // no need to set ChildrenItems list because the constructor created a new empty list

            // ------ Parent ----
            DimensionRoot dimensionParent ;
            if(dimensionRoots.containsKey(dh.getParentId())){
                dimensionParent = dimensionRoots.get(dh.getParentId());
            }
            else{
                dimensionParent = new DimensionRoot();
                dimensionRoots.put(dh.getParentId(),dimensionParent);
            }
            dimensionParent.setId(dh.getParentId().toString());
            dimensionParent.setParentId("null");
            dimensionParent.setName(dh.getParent().getName());
            dimensionParent.addChildrenItem(dimensionChild);
        }

        // Get the root
        List<DimensionRoot> dimensionRootsResult = new ArrayList<DimensionRoot>();
        for(DimensionRoot dr : dimensionRoots.values()){
            if(dr.getParentId().equals("null"))
                dimensionRootsResult.add(dr);
        }
        // Print
        for(DimensionRoot dr: dimensionRootsResult){
            System.out.println("dimensionRootsResult contains "+dimensionRootsResult.size()+" that are : "+ dr);
        }
        return dimensionRootsResult;
    }


}
