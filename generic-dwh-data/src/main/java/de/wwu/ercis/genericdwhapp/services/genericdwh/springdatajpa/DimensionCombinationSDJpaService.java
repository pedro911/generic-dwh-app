package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionCombination;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionCombinationRepository;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionCombinationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DimensionCombinationSDJpaService implements DimensionCombinationService {

    private final DimensionCombinationRepository dimensionCombinationRepository;
    private final DimensionRepository dimensionRepository;

    public DimensionCombinationSDJpaService(DimensionCombinationRepository dimensionCombinationRepository, DimensionRepository dimensionRepository) {
        this.dimensionCombinationRepository = dimensionCombinationRepository;
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public List<DimensionCombination> findAll() {
        List<DimensionCombination> dimensionCombinations = new ArrayList<>();
        dimensionCombinationRepository.findAll().forEach(dimensionCombinations::add);
        return dimensionCombinations;
    }

    @Override
    public List<DimensionCombination> findAllSort(Sort sort) {
        List<DimensionCombination> dimensionCombinations = new ArrayList<>();
        dimensionCombinationRepository.findAll(sort).forEach(dimensionCombinations::add);
        return dimensionCombinations;
    }

    @Override
    public DimensionCombination findById(Long aLong) {
        return dimensionCombinationRepository.findById(aLong).orElse(null);
    }

    @Override
    public DimensionCombination save(DimensionCombination object) {
        return dimensionCombinationRepository.save(object);
    }

    @Override
    public List<DimensionCombination> findByOrderByCombinationIdAsc() {
        return dimensionCombinationRepository.findByOrderByCombinationIdAsc();
    }

    @Override
    public List<Dimension> findDimensionsByCombinationId() {
        List<Dimension> result = new ArrayList<>();
        List<Long> ids = dimensionCombinationRepository.findDimensionsByCombinationId();
        for(Long s : ids ){
            Dimension d = dimensionRepository.findById(s).orElse(null);
            result.add(d);
        }
        return result;
    }
}
