package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionRoot;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionHierarchyRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionHierarchyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Set<DimensionHierarchy> findAll() {
        Set<DimensionHierarchy> dimensionHierarchies = new HashSet<>();
        dimensionHierarchyRepository.findAll().forEach(dimensionHierarchies::add);
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
    public void delete(DimensionHierarchy object) {
        dimensionHierarchyRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        dimensionHierarchyRepository.deleteById(aLong);
    }

    @Override
    public ArrayList<DimensionHierarchy> findByOrderByParentIdAsc() {
        return dimensionHierarchyRepository.findByOrderByParentIdAsc();
    }

    @Override
    public List<DimensionRoot> findAllByRoot() {
        List<DimensionHierarchy> root_dimensions_hierarchy = new ArrayList<>();
        List<Dimension> root_Hierarchies = dimensionRepository.findByRoot();
        DimensionHierarchy dimensionHierarchyResult = new DimensionHierarchy();

        // input
        ArrayList<DimensionHierarchy> pairs = this.findByOrderByParentIdAsc();

        // Arrange - String corresponds to the Id
        Map<Long, DimensionRoot> hm = new HashMap<>();
        // you are using MegaMenuDTO as Linked list with next and before link
        // populate a Map
        for(DimensionHierarchy p:pairs){
            log.debug("parent name ---- ");
            System.out.println(p.getParent().getName());
            //  ----- Child -----
            DimensionRoot mmdChild;
            if(hm.containsKey(p.getChildId())){
                log.debug("child id ----");
                System.out.println(p.getChildId());
                mmdChild = hm.get(p.getChildId());
            }
            else{
                mmdChild = new DimensionRoot();
                hm.put(p.getChildId(),mmdChild);
                log.debug("mmd child ----");
                System.out.println(p.getChildId());
                log.debug("mmd parent ID ");
                System.out.println(p.getParentId());
            }
            mmdChild.setId(p.getChildId().toString());
            mmdChild.setParentId(p.getParent().toString());
            // no need to set ChildrenItems list because the constructor created a new empty list

            // ------ Parent ----
            DimensionRoot mmdParent ;
            if(hm.containsKey(p.getParentId())){
                mmdParent = hm.get(p.getParentId());
            }
            else{
                mmdParent = new DimensionRoot();
                hm.put(p.getParentId(),mmdParent);
            }
            mmdParent.setId(p.getParentId().toString());
            mmdParent.setParentId("null");
            mmdParent.addChildrenItem(mmdChild);
        }

        // Get the root
        List<DimensionRoot> DX = new ArrayList<DimensionRoot>();
        for(DimensionRoot mmd : hm.values()){
            if(mmd.getParentId().equals("null"))
                DX.add(mmd);
        }
        // Print
        for(DimensionRoot mmd: DX){
            System.out.println("DX contains "+DX.size()+" that are : "+ mmd);
        }

        return DX;
    }


}
