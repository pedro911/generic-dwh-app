package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectCombination;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectCombinationRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectCombinationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceObjectCombinationSDJpaService implements ReferenceObjectCombinationService {

    private final ReferenceObjectCombinationRepository referenceObjectCombinationRepository;

    public ReferenceObjectCombinationSDJpaService(ReferenceObjectCombinationRepository referenceObjectCombinationRepository) {
        this.referenceObjectCombinationRepository = referenceObjectCombinationRepository;
    }

    @Override
    public List<ReferenceObjectCombination> findAll() {
        List<ReferenceObjectCombination> referenceObjectCombinations = new ArrayList<>();
        referenceObjectCombinationRepository.findAll().forEach(referenceObjectCombinations::add);
        return referenceObjectCombinations;
    }

    @Override
    public List<ReferenceObjectCombination> findAllSort(Sort sort) {
        List<ReferenceObjectCombination> referenceObjectCombinations = new ArrayList<>();
        referenceObjectCombinationRepository.findAll(sort).forEach(referenceObjectCombinations::add);
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
    public List<ReferenceObjectCombination> findByOrderByCombinationIdAsc() {
        return referenceObjectCombinationRepository.findByOrderByCombinationIdAsc();
    }

    @Override
    public List<ReferenceObjectCombination> findAllBySubordinateId(Long id) {
        return referenceObjectCombinationRepository.findAllBySubordinateId(id);
    }
}
