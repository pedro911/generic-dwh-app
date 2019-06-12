package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DimensionCombinationRepository extends JpaRepository<DimensionCombination, Long> {

    List<DimensionCombination> findByOrderByCombinationIdAsc();

}
