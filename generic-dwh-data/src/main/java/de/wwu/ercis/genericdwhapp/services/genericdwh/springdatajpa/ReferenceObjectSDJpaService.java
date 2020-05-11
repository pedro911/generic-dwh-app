package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceObjectSDJpaService implements ReferenceObjectService {

    private final ReferenceObjectRepository referenceObjectRepository;

    public ReferenceObjectSDJpaService(ReferenceObjectRepository referenceObjectRepository) {
        this.referenceObjectRepository = referenceObjectRepository;
    }

    @Override
    public List<ReferenceObject> findAll() {
        List<ReferenceObject> referenceObjects = new ArrayList<>();
        referenceObjectRepository.findAll().forEach(referenceObjects::add);
        return referenceObjects;
    }

    @Override
    public List<ReferenceObject> findAllSort(Sort sort) {
        List<ReferenceObject> referenceObjects = new ArrayList<>();
        referenceObjectRepository.findAll(sort).forEach(referenceObjects::add);
        return referenceObjects;
    }

    @Override
    public ReferenceObject findById(Long aLong) {
        return referenceObjectRepository.findById(aLong).orElse(null);
    }

    @Override
    public ReferenceObject save(ReferenceObject object) {
        return referenceObjectRepository.save(object);
    }

    @Override
    public List<ReferenceObject> findByOrderByIdAsc() {
        return referenceObjectRepository.findByOrderByIdAsc();
    }

    @Override
    public List<ReferenceObject> findAllByDimensionIn(Dimension dimension) {
        return referenceObjectRepository.findAllByDimensionIn(dimension);
    }

    @Override
    public List<ReferenceObject> findFirst1000ByDimensionIn(Dimension dimension) {
        return referenceObjectRepository.findFirst1000ByDimensionIn(dimension);
    }

    @Override
    public List<ReferenceObject> findAllByNameContaining(String name) {
        return referenceObjectRepository.findAllByNameContaining(name.toUpperCase());
    }

    @Override
    public List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name) {
        return referenceObjectRepository.findAllByDimensionInAndNameContaining(dimension, name);
    }

    @Override
    public ReferenceObject findFirstByDimension(Dimension dimension) {
        return referenceObjectRepository.findFirstByDimension(dimension).orElse(null);
    }
}
