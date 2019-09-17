package de.wwu.ercis.genericdwhapp.services.stats.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.stats.DBModel;
import de.wwu.ercis.genericdwhapp.repositories.stats.DBModelRepository;
import de.wwu.ercis.genericdwhapp.services.stats.DBModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class DBModelSDJpaService implements DBModelService {

    private final DBModelRepository dbModelRepository;

    public DBModelSDJpaService(DBModelRepository dbModelRepository) {
        this.dbModelRepository = dbModelRepository;
    }

    @Override
    public Set<DBModel> findAll() {
        Set<DBModel> dbModels = new HashSet<>();
        dbModelRepository.findAll().forEach(dbModels::add);
        return dbModels;
    }

    @Override
    public DBModel findById(Long aLong) {
        return dbModelRepository.findById(aLong).orElse(null);
    }

    @Override
    public DBModel save(DBModel object) {
        return dbModelRepository.save(object);
    }

    @Override
    public void delete(DBModel object) {
        dbModelRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        dbModelRepository.deleteById(aLong);
    }
}
