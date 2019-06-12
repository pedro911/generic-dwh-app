package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectCombination;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectCombinationRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectCombinationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

    @Override
    public List<ReferenceObjectCombination> findByOrderByCombinationIdAsc() {
        return referenceObjectCombinationRepository.findByOrderByCombinationIdAsc();
    }
}
