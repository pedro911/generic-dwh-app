package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioCombination;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.RatioCombinationRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.RatioCombinationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RatioCombinationSDJpaService implements RatioCombinationService {

    private final RatioCombinationRepository ratioCombinationRepository;

    public RatioCombinationSDJpaService(RatioCombinationRepository ratioCombinationRepository) {
        this.ratioCombinationRepository = ratioCombinationRepository;
    }

    @Override
    public Set<RatioCombination> findAll() {
        Set<RatioCombination> ratioCombinations = new HashSet<>();
        ratioCombinationRepository.findAll().forEach(ratioCombinations::add);
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
    public void delete(RatioCombination object) {
        ratioCombinationRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ratioCombinationRepository.deleteById(aLong);
    }

    @Override
    public List<RatioCombination> findByOrderByIdAsc() {
        return ratioCombinationRepository.findByOrderByIdAsc();
    }
}
