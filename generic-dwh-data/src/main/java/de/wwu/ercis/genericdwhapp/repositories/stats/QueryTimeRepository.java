package de.wwu.ercis.genericdwhapp.repositories.stats;

import de.wwu.ercis.genericdwhapp.model.stats.DBModel;
import de.wwu.ercis.genericdwhapp.model.stats.QueryTime;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface QueryTimeRepository extends JpaRepository<QueryTime, Long>, NativeQuery {

    Set<QueryTime> findAllByDbModel(DBModel dbModel);



}
