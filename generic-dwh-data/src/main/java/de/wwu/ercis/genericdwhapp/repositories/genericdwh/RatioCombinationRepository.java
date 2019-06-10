package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatioCombinationRepository extends JpaRepository<RatioCombination, Long> {

    List<RatioCombination> findByOrderByIdAsc();
}
