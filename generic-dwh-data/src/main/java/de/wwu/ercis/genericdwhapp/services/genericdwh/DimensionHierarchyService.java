package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionRoot;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.ArrayList;
import java.util.List;

public interface DimensionHierarchyService extends CrudService<DimensionHierarchy, Long> {

    List<DimensionHierarchy> findByOrderByParentIdAsc();

    ArrayList<DimensionRoot> findAllByRoot();

}
