package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReferenceObjectSDJpaService implements ReferenceObjectService {

    private final ReferenceObjectRepository referenceObjectRepository;

    public ReferenceObjectSDJpaService(ReferenceObjectRepository referenceObjectRepository) {
        this.referenceObjectRepository = referenceObjectRepository;
    }

    @Override
    public Set<ReferenceObject> findAll() {
        Set<ReferenceObject> referenceObjects = new HashSet<>();
        referenceObjectRepository.findAll().forEach(referenceObjects::add);
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
    public void delete(ReferenceObject object) {
        referenceObjectRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        referenceObjectRepository.deleteById(aLong);
    }

    @Override
    public List<ReferenceObject> findByOrderByIdAsc() {
        return referenceObjectRepository.findByOrderByIdAsc();
    }

    @Override
    public List<Fact> findSpecial(String query){

        List<Fact> result = referenceObjectRepository.findSpecial(query);

        return result;
    }

    @Override
    public List<ReferenceObject> findAllByDimensionIn(Dimension dimension) {
        return referenceObjectRepository.findAllByDimensionIn(dimension);
    }

    @Override
    public List<ReferenceObject> findAllByNameContaining(String name) {
        return referenceObjectRepository.findAllByNameContaining(name.toUpperCase());
    }

    @Override
    public List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name) {
        return referenceObjectRepository.findAllByDimensionInAndNameContaining(dimension, name);
    }
}
