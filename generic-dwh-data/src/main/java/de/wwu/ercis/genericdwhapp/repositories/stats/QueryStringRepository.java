package de.wwu.ercis.genericdwhapp.repositories.stats;

import de.wwu.ercis.genericdwhapp.model.stats.QueryString;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface QueryStringRepository extends JpaRepository<QueryString, Long>, NativeQuery {

    Optional<QueryString> findQueryStringByRefObjRatios(String s);

    List<QueryString> findAllByOrderByQueryStringId();

    List<QueryString> findAllByQueryStringIdIn(List<Long> queryIds);

}
