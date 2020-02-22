package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    List<Dimension> findByOrderByIdAsc();

    Dimension findFirstByOrderByIdDesc();

    List<Dimension> findAll(Sort sort);

    @Query(value = "SELECT * FROM dimension_hierarchy dh LEFT JOIN dimension d ON d.id = dh.parent_id\n" +
            "WHERE dh.parent_id NOT IN (select dimension_hierarchy.child_id FROM dimension_hierarchy)", nativeQuery = true)
    List<Dimension> findByRoot();

    @Query(value = "SELECT * FROM dimension d WHERE NOT EXISTS \n" +
            "(SELECT 1 FROM dimension_hierarchy dh \n" +
            "WHERE dh.child_id = d.id) AND NOT EXISTS \n" +
            "(SELECT 1 FROM dimension_combination dc WHERE dc.combination_id = d.id) \n" +
            "ORDER BY d.is_time ASC, d.name ASC", nativeQuery = true)
    List<Dimension> findAllRoots();

    Optional<Dimension> findByName(String name);

    @Query(value = "SELECT * FROM dimension d WHERE d.id IN \n" +
            " (SELECT dc.subordinate_id FROM dimension_combination dc WHERE dc.combination_id = :dimensionCombinationId) \n" +
            " ORDER BY d.name ASC", nativeQuery = true)
    List<Dimension> findAtomicLevels(String dimensionCombinationId);

    @Query(value = "SELECT * FROM dimension d WHERE d.id IN \n" +
            "(SELECT dh.child_id FROM dimension_hierarchy dh WHERE dh.parent_id = :parentId) \n" +
            "ORDER BY d.name ASC", nativeQuery = true)
    Optional<Dimension> findChildByParent(Long parentId);

    @Query(value = "SELECT * FROM dimension d WHERE d.id IN \n" +
            "(SELECT dh.child_id FROM dimension_hierarchy dh WHERE dh.parent_id = :parentId) \n" +
            "ORDER BY d.name ASC", nativeQuery = true)
    List<Dimension> findChildByParentList(Long parentId);

    @Query(value = "SELECT * FROM dimension\n" +
            "WHERE id IN(SELECT DISTINCT child_id FROM dimension_hierarchy) ORDER BY id", nativeQuery = true)
    List<Dimension> findAtomicAndHierarchyLevels();

}
