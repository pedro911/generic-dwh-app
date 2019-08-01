package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FactRepository extends JpaRepository<Fact, Long>, ResultParser {

    Optional<Fact> findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio);

    @Query(value = "SELECT f.reference_object_id, f.ratio_id, f.value FROM fact f \n" +
            "INNER JOIN reference_object ro ON ro.id = f.reference_object_id " +
            "AND ro.dimension_id = :dimensionId AND f.ratio_id = :ratioId", nativeQuery = true)
    List<Fact> findByDimensionIdAndRatioId(@Param("dimensionId") String dimensionId, @Param("ratioId") String ratioId);

    @Query(value =
            "SELECT _ro_result.subordinate_id AS 'reference_object_id', _fact.ratio_id, SUM(_fact.value) AS 'value'\n" +
                    "FROM reference_object ro\n" +
                    "INNER JOIN reference_object_combination _ro_result ON _ro_result.combination_id = ro.id\n" +
                    "INNER JOIN reference_object _result ON _result.id = _ro_result.subordinate_id AND _result.dimension_id = :dimensionId\n" +
                    "INNER JOIN fact _fact ON _fact.reference_object_id = ro.id AND _fact.ratio_id = :ratioId\n" +
                    "WHERE ro.dimension_id IN (:dimensionCombination)\n" +
                    "GROUP BY _result.id", nativeQuery = true)
    List<Fact> genericDWHResults(@Param("dimensionId") String dimensionId, @Param("ratioId") String ratioId,
                                 @Param("dimensionCombination") String dimensionCombination);

    @Modifying
    @Query(value = "INSERT INTO fact values (:roId,:ratioId,:fvalue)", nativeQuery = true)
    void insertNewFact(@Param("roId") Long roId, @Param("ratioId") Long ratioId,
                       @Param("fvalue") Double fvalue);
}
