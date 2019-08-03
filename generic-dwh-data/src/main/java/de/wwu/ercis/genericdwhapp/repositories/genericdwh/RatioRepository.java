package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RatioRepository extends JpaRepository<Ratio, Long> {

    List<Ratio> findByOrderByIdAsc();

    @Query(value = "SELECT id, name FROM ratio r\n" +
            "INNER JOIN fact f ON f.ratio_id = r.id AND f.reference_object_id = \n" +
            "(SELECT id FROM reference_object WHERE dimension_id = :dimensionId LIMIT 1)", nativeQuery = true)
    List<Ratio> findByDimensionCombinationId(Long dimensionId);


}
