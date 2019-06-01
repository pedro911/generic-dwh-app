package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.Dimension;
import de.wwu.ercis.genericdwhapp.repositories.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.DimensionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DimensionSDJpaService implements DimensionService {

    private final DimensionRepository dimensionRepository;

    public DimensionSDJpaService(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public Set<Dimension> findAll() {
        Set<Dimension> dimensions = new HashSet<>();
        dimensionRepository.findAll().forEach(dimensions::add);
        return dimensions;
    }

    @Override
    public Dimension findById(Long aLong) {
        return dimensionRepository.findById(aLong).orElse(null);
    }

    @Override
    public Dimension save(Dimension object) {
        return dimensionRepository.save(object);
    }

    @Override
    public void delete(Dimension object) {
        dimensionRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        dimensionRepository.deleteById(aLong);
    }
}
