package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface ReferenceObjectService extends CrudService<ReferenceObject, Long> {

    List<ReferenceObject> findByOrderByIdAsc();
}
