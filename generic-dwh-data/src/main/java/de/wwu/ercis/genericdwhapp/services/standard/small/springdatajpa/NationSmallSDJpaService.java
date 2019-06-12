package de.wwu.ercis.genericdwhapp.services.standard.small.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.small.NationSmallRepository;
import de.wwu.ercis.genericdwhapp.services.standard.small.NationSmallService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("alldb")
public class NationSmallSDJpaService implements NationSmallService {

    private final NationSmallRepository nationSmallRepository;

    public NationSmallSDJpaService(NationSmallRepository nationSmallRepository) {
        this.nationSmallRepository = nationSmallRepository;
    }

    @Override
    public Set<NationEntity> findAll() {
        Set<NationEntity> nationEntities = new HashSet<>();
        nationSmallRepository.findAll().forEach(nationEntities::add);
        return nationEntities;
    }

    @Override
    public NationEntity findById(Long aLong) {
        return nationSmallRepository.findById(aLong).orElse(null);
    }

    @Override
    public NationEntity save(NationEntity object) {
        return nationSmallRepository.save(object);
    }

    @Override
    public void delete(NationEntity object) {
        nationSmallRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        nationSmallRepository.deleteById(aLong);
    }

    @Override
    public List<NationEntity> findAll(Sort sort) {
        return nationSmallRepository.findAll(sort);
    }
}
