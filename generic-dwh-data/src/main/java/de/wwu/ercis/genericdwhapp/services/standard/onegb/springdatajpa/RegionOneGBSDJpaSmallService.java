package de.wwu.ercis.genericdwhapp.services.standard.onegb.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import de.wwu.ercis.genericdwhapp.repositories.standard.onegb.RegionOneGBRepository;
import de.wwu.ercis.genericdwhapp.services.standard.onegb.RegionOnegbService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegionOneGBSDJpaSmallService implements RegionOnegbService {

    private final RegionOneGBRepository regionOneGBRepository;

    public RegionOneGBSDJpaSmallService(RegionOneGBRepository regionOneGBRepository) {
        this.regionOneGBRepository = regionOneGBRepository;
    }

    @Override
    public Set<RegionEntity> findAll() {
        Set<RegionEntity> regionEntities = new HashSet<>();
        regionOneGBRepository.findAll().forEach(regionEntities::add);
        return regionEntities;
    }

    @Override
    public RegionEntity findById(Long aLong) {
        return regionOneGBRepository.findById(aLong).orElse(null);
    }

    @Override
    public RegionEntity save(RegionEntity object) {
        return regionOneGBRepository.save(object);
    }

    @Override
    public void delete(RegionEntity object) {
        regionOneGBRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        regionOneGBRepository.deleteById(aLong);
    }
}
