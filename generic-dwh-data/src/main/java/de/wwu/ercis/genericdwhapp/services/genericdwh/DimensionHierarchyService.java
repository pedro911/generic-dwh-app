package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionRoot;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface DimensionHierarchyService extends GenericDWHService<DimensionHierarchy, Long> {

    List<DimensionHierarchy> findByOrderByParentIdAsc();

    List<DimensionRoot> findAllByRoot();

}
