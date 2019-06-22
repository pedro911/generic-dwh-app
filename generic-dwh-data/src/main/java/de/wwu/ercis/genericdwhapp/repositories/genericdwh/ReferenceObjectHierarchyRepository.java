package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReferenceObjectHierarchyRepository extends JpaRepository<ReferenceObjectHierarchy, Long> {

    List<ReferenceObjectHierarchy> findByOrderByParentIdAsc();

}
