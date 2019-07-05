package de.wwu.ercis.genericdwhapp.services.tpchstandard.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.tpchstandard.RegionEntity;
import de.wwu.ercis.genericdwhapp.repositories.tpchstandard.RegionRepository;
import de.wwu.ercis.genericdwhapp.services.tpchstandard.RegionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegionSDJpaService implements RegionService {

    private final RegionRepository regionRepository;

    public RegionSDJpaService(RegionRepository regionSmallRepository) {
        this.regionRepository = regionSmallRepository;
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

    @Override
    public List<RegionEntity> findAll(Sort sort) {
        return regionRepository.findAll(sort);
    }
}
