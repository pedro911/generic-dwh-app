package de.wwu.ercis.genericdwhapp.services.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.repositories.generichdwh.RatioRepository;
import de.wwu.ercis.genericdwhapp.services.RatioService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class RatioSDJpaService implements RatioService {

    private RatioRepository ratioRepository;

    public RatioSDJpaService(RatioRepository ratioRepository) {
        this.ratioRepository = ratioRepository;
    }

    @Override
    public Set<Ratio> findAll() {
        Set<Ratio> ratios = new HashSet<>();
        ratioRepository.findAll().forEach(ratios::add);
        return ratios;
    }

    @Override
    public Ratio findById(Long aLong) {
        return ratioRepository.findById(aLong).orElse(null);
    }

    @Override
    public Ratio save(Ratio object) {
        return ratioRepository.save(object);
    }

    @Override
    public void delete(Ratio object) {
        ratioRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ratioRepository.deleteById(aLong);
    }
}
