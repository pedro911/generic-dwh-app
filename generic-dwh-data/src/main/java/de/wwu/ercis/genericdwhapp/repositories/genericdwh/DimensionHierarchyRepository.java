package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DimensionHierarchyRepository extends JpaRepository<DimensionHierarchy, Long> {

    List<DimensionHierarchy> findByOrderByIdAsc();

}
