package de.wwu.ercis.genericdwhapp.services.standard.small.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.small.RegionSmallRepository;
import de.wwu.ercis.genericdwhapp.services.standard.small.RegionSmallService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("alldb")
public class RegionSmallSDJpaService implements RegionSmallService {

    private final RegionSmallRepository regionSmallRepository;

    public RegionSmallSDJpaService(RegionSmallRepository regionSmallRepository) {
        this.regionSmallRepository = regionSmallRepository;
    }

    @Override
    public Set<RegionEntity> findAll() {
        Set<RegionEntity> regionEntities = new HashSet<>();
        regionSmallRepository.findAll().forEach(regionEntities::add);
        return regionEntities;
    }

    @Override
    public RegionEntity findById(Long aLong) {
        return regionSmallRepository.findById(aLong).orElse(null);
    }

    @Override
    public RegionEntity save(RegionEntity object) {
        return regionSmallRepository.save(object);
    }

    @Override
    public void delete(RegionEntity object) {
        regionSmallRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        regionSmallRepository.deleteById(aLong);
    }
}
