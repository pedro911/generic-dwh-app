package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceObjectHierarchyRepository extends JpaRepository<ReferenceObjectHierarchy, Long> {

    List<ReferenceObjectHierarchy> findByOrderByParentIdAsc();

}
