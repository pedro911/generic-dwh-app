package de.wwu.ercis.genericdwhapp.services.genericdwh.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.repositories.genericdwh.ReferenceObjectRepository;
import de.wwu.ercis.genericdwhapp.services.genericdwh.ReferenceObjectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
}
