package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DimensionSDJpaService implements DimensionService {

    private final DimensionRepository dimensionRepository;

    public DimensionSDJpaService(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public Set<Dimension> findAll() {
        log.debug("Test log from lombok slf4j");
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

    @Override
    public List<Dimension> findByOrderByIdAsc() {return dimensionRepository.findByOrderByIdAsc(); }

    @Override
    public List<Dimension> findAll(Sort sort) {
        return dimensionRepository.findAll(sort);
    }

}