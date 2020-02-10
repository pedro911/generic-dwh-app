package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionCombination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DimensionCombinationRepository extends JpaRepository<DimensionCombination, Long> {

    List<DimensionCombination> findByOrderByCombinationIdAsc();

    @Query(value = "SELECT DISTINCT dimension_combination.combination_id as id FROM dimension_combination " +
            "WHERE dimension_combination.show_on = TRUE ", nativeQuery = true)
    List<Long> findDimensionsByCombinationId();

    DimensionCombination findFirstByOrderByCombinationIdDesc();

}
