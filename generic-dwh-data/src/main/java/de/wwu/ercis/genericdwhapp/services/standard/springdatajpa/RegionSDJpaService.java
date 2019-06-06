package de.wwu.ercis.genericdwhapp.services.standard.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.RegionRepository;
import de.wwu.ercis.genericdwhapp.services.standard.RegionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegionSDJpaService implements RegionService {

    private final RegionRepository regionRepository;

    public RegionSDJpaService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Set<RegionEntity> findAll() {
        Set<RegionEntity> regionEntities = new HashSet<>();
        regionRepository.findAll().forEach(regionEntities::add);
        return regionEntities;
    }

    @Override
    public RegionEntity findById(Long aLong) {
        return regionRepository.findById(aLong).orElse(null);
    }

    @Override
    public RegionEntity save(RegionEntity object) {
        return regionRepository.save(object);
    }

    @Override
    public void delete(RegionEntity object) {
        regionRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        regionRepository.deleteById(aLong);
    }
}
