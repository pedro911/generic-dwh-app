package de.wwu.ercis.genericdwhapp.repositories.snowflakeschema;

import de.wwu.ercis.genericdwhapp.model.starschema.StarSchemaFact;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SnowflakeRepository extends JpaRepository<StarSchemaFact, Long>, NativeQuery {

}
