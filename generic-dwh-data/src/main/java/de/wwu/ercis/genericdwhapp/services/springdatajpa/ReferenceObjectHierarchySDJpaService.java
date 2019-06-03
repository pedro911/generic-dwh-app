package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;
import de.wwu.ercis.genericdwhapp.repositories.generichdwh.ReferenceObjectHierarchyRepository;
import de.wwu.ercis.genericdwhapp.services.ReferenceObjectHierarchyService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReferenceObjectHierarchySDJpaService implements ReferenceObjectHierarchyService {

    private final ReferenceObjectHierarchyRepository referenceObjectHierarchyRepository;

    public ReferenceObjectHierarchySDJpaService(ReferenceObjectHierarchyRepository referenceObjectHierarchyRepository) {
        this.referenceObjectHierarchyRepository = referenceObjectHierarchyRepository;
    }

    @Override
    public Set<ReferenceObjectHierarchy> findAll() {
        Set<ReferenceObjectHierarchy> referenceObjectHierarchies = new HashSet<>();
        referenceObjectHierarchyRepository.findAll().forEach(referenceObjectHierarchies::add);
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
    public void delete(ReferenceObjectHierarchy object) {
        referenceObjectHierarchyRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        referenceObjectHierarchyRepository.deleteById(aLong);
    }
}
