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

    @Query(value = "SELECT DISTINCT dimension_combination.combination_id as id FROM dimension_combination", nativeQuery = true)
    List<Long> findDimensionsByCombinationId();

    @Query(value = "SELECT subordinate_id FROM dimension_combination WHERE combination_id= :id", nativeQuery = true)
    List<Long> findDimensionCombinationsByCombinationId(Long id);

}
