package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectHierarchyRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectHierarchyService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceObjectHierarchySDJpaService implements ReferenceObjectHierarchyService {

    private final ReferenceObjectHierarchyRepository referenceObjectHierarchyRepository;

    public ReferenceObjectHierarchySDJpaService(ReferenceObjectHierarchyRepository referenceObjectHierarchyRepository) {
        this.referenceObjectHierarchyRepository = referenceObjectHierarchyRepository;
    }

    @Override
    public List<ReferenceObjectHierarchy> findAll() {
        List<ReferenceObjectHierarchy> referenceObjectHierarchies = new ArrayList<>();
        referenceObjectHierarchyRepository.findAll().forEach(referenceObjectHierarchies::add);
        return referenceObjectHierarchies;
    }

    @Override
    public List<ReferenceObjectHierarchy> findAllSort(Sort sort) {
        List<ReferenceObjectHierarchy> referenceObjectHierarchies = new ArrayList<>();
        referenceObjectHierarchyRepository.findAll(sort).forEach(referenceObjectHierarchies::add);
        return referenceObjectHierarchies;
    }

    @Override
    public ReferenceObjectHierarchy findById(Long aLong) {
        return referenceObjectHierarchyRepository.findById(aLong).orElse(null);
    }

    @Override
    public ReferenceObjectHierarchy save(ReferenceObjectHierarchy object) {
        return referenceObjectHierarchyRepository.save(object);
    }

    @Override
    public List<ReferenceObjectHierarchy> findByOrderByParentIdAsc() {
        return referenceObjectHierarchyRepository.findByOrderByParentIdAsc();
    }
}
