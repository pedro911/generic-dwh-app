package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectHierarchy;

import java.util.List;

public interface ReferenceObjectHierarchyService extends CrudService<ReferenceObjectHierarchy, Long> {

    List<ReferenceObjectHierarchy> findByOrderByIdAsc();

}
