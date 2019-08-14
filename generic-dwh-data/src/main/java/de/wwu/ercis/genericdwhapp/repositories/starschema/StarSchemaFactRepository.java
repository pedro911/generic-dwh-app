package de.wwu.ercis.genericdwhapp.repositories.starschema;

import de.wwu.ercis.genericdwhapp.model.starschema.StarSchemaFact;
import de.wwu.ercis.genericdwhapp.repositories.ResultParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StarSchemaFactRepository extends JpaRepository<StarSchemaFact, Long>, ResultParser {

    @Query(value = "SELECT :dimension as 'name', SUM(:ratio) as 'value' FROM dim_customer c\n" +
            "INNER JOIN fact f ON f.FK_CUSTOMER = c.PK_CUSTKEY\n" +
            "GROUP BY :dimension", nativeQuery = true)
    List<Object[]> facts(@Param("dimension") String dimension, @Param("ratio") String ratio);

    @Query(value = "SELECT r_name as 'name', SUM(turnover) as 'value' FROM dim_customer c\n" +
            "INNER JOIN fact f ON f.FK_CUSTOMER = c.PK_CUSTKEY\n" +
            "GROUP BY r_name", nativeQuery = true)
    List<Object[]> facts();

}
