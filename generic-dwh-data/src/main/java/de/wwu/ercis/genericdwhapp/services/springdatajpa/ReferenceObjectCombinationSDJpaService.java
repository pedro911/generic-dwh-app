package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.ReferenceObjectCombination;
import de.wwu.ercis.genericdwhapp.repositories.ReferenceObjectCombinationRepository;
import de.wwu.ercis.genericdwhapp.services.ReferenceObjectCombinationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReferenceObjectCombinationSDJpaService implements ReferenceObjectCombinationService {

    private final ReferenceObjectCombinationRepository referenceObjectCombinationRepository;

    public ReferenceObjectCombinationSDJpaService(ReferenceObjectCombinationRepository referenceObjectCombinationRepository) {
        this.referenceObjectCombinationRepository = referenceObjectCombinationRepository;
    }

    @Override
    public Set<ReferenceObjectCombination> findAll() {
        Set<ReferenceObjectCombination> referenceObjectCombinations = new HashSet<>();
        referenceObjectCombinationRepository.findAll().forEach(referenceObjectCombinations::add);
        return referenceObjectCombinations;
    }

    @Override
    public ReferenceObjectCombination findById(Long aLong) {
        return referenceObjectCombinationRepository.findById(aLong).orElse(null);
    }

    @Override
    public ReferenceObjectCombination save(ReferenceObjectCombination object) {
        return referenceObjectCombinationRepository.save(object);
    }

    @Override
    public void delete(ReferenceObjectCombination object) {
        referenceObjectCombinationRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        referenceObjectCombinationRepository.deleteById(aLong);
    }
}
