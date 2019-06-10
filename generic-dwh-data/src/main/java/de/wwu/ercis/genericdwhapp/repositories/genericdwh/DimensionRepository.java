package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    List<Dimension> findByOrderByIdAsc();

    List<Dimension> findAll(Sort sort);
}
