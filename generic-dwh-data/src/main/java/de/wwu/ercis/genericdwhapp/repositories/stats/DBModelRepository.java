package de.wwu.ercis.genericdwhapp.repositories.stats;

import de.wwu.ercis.genericdwhapp.model.stats.DBModel;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface DBModelRepository extends JpaRepository<DBModel, Long>, NativeQuery {

    Set<DBModel> findAllByDbNameContaining(String s);

    Optional<DBModel> findDBModelByDbName(String name);

}
