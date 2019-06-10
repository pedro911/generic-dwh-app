package de.wwu.ercis.genericdwhapp.services.standard.onegb.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.onegb.NationOneGBRepository;
import de.wwu.ercis.genericdwhapp.services.standard.onegb.NationOnegbService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("alldb")
public class NationOneGBSDJpaService implements NationOnegbService {

    private final NationOneGBRepository nationOneGBRepository;

    public NationOneGBSDJpaService(NationOneGBRepository nationOneGBRepository) {
        this.nationOneGBRepository = nationOneGBRepository;
    }

    @Override
    public Set<NationEntity> findAll() {
        Set<NationEntity> nationEntities = new HashSet<>();
        nationOneGBRepository.findAll().forEach(nationEntities::add);
        return nationEntities;
    }

    @Override
    public NationEntity findById(Long aLong) {
        return nationOneGBRepository.findById(aLong).orElse(null);
    }

    @Override
    public NationEntity save(NationEntity object) {
        return nationOneGBRepository.save(object);
    }

    @Override
    public void delete(NationEntity object) {
        nationOneGBRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        nationOneGBRepository.deleteById(aLong);
    }

    @Override
    public List<NationEntity> findAll(Sort sort) {
        return nationOneGBRepository.findAll(sort);
    }
}
