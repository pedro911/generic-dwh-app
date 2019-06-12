package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceObjectCombinationRepository extends JpaRepository<ReferenceObjectCombination, Long> {

    List<ReferenceObjectCombination> findByOrderByCombinationIdAsc();

}
