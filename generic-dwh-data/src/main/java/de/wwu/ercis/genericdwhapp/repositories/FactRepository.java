package de.wwu.ercis.genericdwhapp.repositories;

import de.wwu.ercis.genericdwhapp.model.Fact;
import org.springframework.data.repository.CrudRepository;

public interface FactRepository extends CrudRepository<Fact, Long> {
}
