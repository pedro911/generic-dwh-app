package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioCombination;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.RatioCombinationRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.RatioCombinationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatioCombinationSDJpaService implements RatioCombinationService {

    private final RatioCombinationRepository ratioCombinationRepository;

    public RatioCombinationSDJpaService(RatioCombinationRepository ratioCombinationRepository) {
        this.ratioCombinationRepository = ratioCombinationRepository;
    }

    @Override
    public List<RatioCombination> findAll() {
        List<RatioCombination> ratioCombinations = new ArrayList<>();
        ratioCombinationRepository.findAll().forEach(ratioCombinations::add);
        return ratioCombinations;
    }

    @Override
    public List<RatioCombination> findAllSort(Sort sort) {
        List<RatioCombination> ratioCombinations = new ArrayList<>();
        ratioCombinationRepository.findAll(sort).forEach(ratioCombinations::add);
        return ratioCombinations;
    }

    @Override
    public RatioCombination findById(Long aLong) {
        return ratioCombinationRepository.findById(aLong).orElse(null);
    }

    @Override
    public RatioCombination save(RatioCombination object) {
        return ratioCombinationRepository.save(object);
    }

    @Override
    public List<RatioCombination> findByOrderByCombinationIdAsc() {
        return ratioCombinationRepository.findByOrderByCombinationIdAsc();
    }
}
