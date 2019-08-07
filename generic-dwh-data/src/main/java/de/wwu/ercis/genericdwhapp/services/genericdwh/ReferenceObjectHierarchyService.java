package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface ReferenceObjectHierarchyService extends GenericDWHService<ReferenceObjectHierarchy, Long> {

    List<ReferenceObjectHierarchy> findByOrderByParentIdAsc();

}
