package de.wwu.ercis.genericdwhapp.repositories.starschema;

import de.wwu.ercis.genericdwhapp.model.starschema.StarSchemaFact;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StarRepository extends JpaRepository<StarSchemaFact, Long>, NativeQuery {

}
