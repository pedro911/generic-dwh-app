package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.repositories.generichdwh.DimensionHierarchyRepository;
import de.wwu.ercis.genericdwhapp.services.DimensionHierarchyService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DimensionHierarchySDJpaService implements DimensionHierarchyService {

    private final DimensionHierarchyRepository dimensionHierarchyRepository;

    public DimensionHierarchySDJpaService(DimensionHierarchyRepository dimensionHierarchyRepository) {
        this.dimensionHierarchyRepository = dimensionHierarchyRepository;
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
}
