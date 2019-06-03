package de.wwu.ercis.genericdwhapp.repositories.generichdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import org.springframework.data.repository.CrudRepository;

public interface FactRepository extends CrudRepository<Fact, Long> {
}
