package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;

import java.util.List;

public interface DimensionHierarchyService extends CrudService<DimensionHierarchy, Long> {

    List<DimensionHierarchy> findByOrderByParentIdAsc();

}
