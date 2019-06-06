package de.wwu.ercis.genericdwhapp.services.standard.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.NationRepository;
import de.wwu.ercis.genericdwhapp.services.standard.NationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NationSDJpaService implements NationService {

    private final NationRepository nationRepository;

    public NationSDJpaService(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    @Override
    public Set<NationEntity> findAll() {
        Set<NationEntity> nationEntities = new HashSet<>();
        nationRepository.findAll().forEach(nationEntities::add);
        return nationEntities;
    }

    @Override
    public NationEntity findById(Long aLong) {
        return nationRepository.findById(aLong).orElse(null);
    }

    @Override
    public NationEntity save(NationEntity object) {
        return nationRepository.save(object);
    }

    @Override
    public void delete(NationEntity object) {
        nationRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        nationRepository.deleteById(aLong);
    }
}
