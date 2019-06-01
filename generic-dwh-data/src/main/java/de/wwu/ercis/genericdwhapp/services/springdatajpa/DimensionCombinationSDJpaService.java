package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.DimensionCombination;
import de.wwu.ercis.genericdwhapp.repositories.DimensionCombinationRepository;
import de.wwu.ercis.genericdwhapp.repositories.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.DimensionCombinationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DimensionCombinationSDJpaService implements DimensionCombinationService {

    private final DimensionCombinationRepository dimensionCombinationRepository;
    private final DimensionRepository dimensionRepository;

    public DimensionCombinationSDJpaService(DimensionCombinationRepository dimensionCombinationRepository, DimensionRepository dimensionRepository) {
        this.dimensionCombinationRepository = dimensionCombinationRepository;
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public Set<DimensionCombination> findAll() {
        Set<DimensionCombination> dimensionCombinations = new HashSet<>();
        dimensionCombinationRepository.findAll().forEach(dimensionCombinations::add);
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
    public void delete(DimensionCombination object) {
        dimensionCombinationRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        dimensionCombinationRepository.deleteById(aLong);
    }
}
