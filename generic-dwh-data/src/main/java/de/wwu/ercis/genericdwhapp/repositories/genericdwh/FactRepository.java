package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FactRepository extends JpaRepository<Fact, Long> {

    List<Fact> findByOrderByRatioIdAsc();

    Optional<Fact> findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio);

    Optional<Fact> findByReferenceObjectIdAndRatioId(Long roId, Long ratioId);

    Optional<Fact> findByReferenceObjectId(Long id);

    Optional<Fact> findFirstByReferenceObjectIdAndRatioId(Long roId, Long ratioId);

    @Query(value = "SELECT f.reference_object_id, f.ratio_id, f.value FROM fact f \n" +
            "INNER JOIN reference_object ro ON ro.id = f.reference_object_id " +
            "AND ro.dimension_id = :dimensionId AND f.ratio_id = :ratioId", nativeQuery = true)
    List<Fact> findByDimensionIdAndRatioId(@Param("dimensionId") String dimensionId, @Param("ratioId") String ratioId);


}
