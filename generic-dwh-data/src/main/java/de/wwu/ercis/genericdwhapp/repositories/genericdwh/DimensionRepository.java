package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    List<Dimension> findByOrderByIdAsc();

    List<Dimension> findAll(Sort sort);
}
