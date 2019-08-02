package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DimensionHierarchyRepository extends JpaRepository<DimensionHierarchy, Long> {

    List<DimensionHierarchy> findByOrderByParentIdAsc();

    List<DimensionHierarchy> findAllByParentId(Long id);



}
