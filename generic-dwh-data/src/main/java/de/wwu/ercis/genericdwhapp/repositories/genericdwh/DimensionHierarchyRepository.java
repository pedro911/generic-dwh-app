package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.repositories.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DimensionHierarchyRepository extends JpaRepository<DimensionHierarchy, Long>, NativeQuery {

    List<DimensionHierarchy> findByOrderByParentIdAsc();

    List<DimensionHierarchy> findAllByParentId(Long id);



}
