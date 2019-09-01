package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.DimensionRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.DimensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DimensionSDJpaService implements DimensionService {

    private final DimensionRepository dimensionRepository;

    public DimensionSDJpaService(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    @Override
    public List<Dimension> findAll() {
        List<Dimension> dimensions = new ArrayList<>();
        dimensionRepository.findAll().forEach(dimensions::add);
        return dimensions;
    }

    @Override
    public List<Dimension> findAllSort(Sort sort) {
        List<Dimension> dimensions = new ArrayList<>();
        dimensionRepository.findAll(sort).forEach(dimensions::add);
        return dimensions;
    }

    @Override
    public Dimension findById(Long aLong) {
        return dimensionRepository.findById(aLong).orElse(null);
    }

    @Override
    public Dimension save(Dimension object) {
        return dimensionRepository.save(object);
    }

    @Override
    public List<Dimension> findByOrderByIdAsc() {return dimensionRepository.findByOrderByIdAsc(); }

    @Override
    public List<Dimension> findAll(Sort sort) {
        return dimensionRepository.findAll(sort);
    }

    @Override
    public List<Dimension> findByRoot() {
        //maybe: first find roots (ok), then add all hierarchies from that root
        //or with sql?! getChildId from root, check if exits childId = parentId, if yes add to list?!
        //or implement method on hierarchies, with this list find next parents/childs

        return dimensionRepository.findByRoot();
    }

    @Override
    public Dimension findByName(String name) {
        return dimensionRepository.findByName(name).orElse(null);
    }

}
