package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactRepository extends JpaRepository<Fact, Long> {

    List<Fact> findByOrderByIdAsc();
}
