package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.FactRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.FactService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FactSDJpaService implements FactService {

    private final FactRepository factRepository;

    public FactSDJpaService(FactRepository factRepository) {
        this.factRepository = factRepository;
    }

    @Override
    public Set<Fact> findAll() {
        Set<Fact> facts = new HashSet<>();
        factRepository.findAll().forEach(facts::add);
        return facts;
    }

    @Override
    public Fact findById(Long aLong) {
        return factRepository.findById(aLong).orElse(null);
    }

    @Override
    public Fact save(Fact object) {
        return factRepository.save(object);
    }

    @Override
    public void delete(Fact object) {
        factRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        factRepository.deleteById(aLong);
    }

    @Override
    public List<Fact> findByOrderByRatioIdAsc() {
        return factRepository.findByOrderByRatioIdAsc();
    }
}