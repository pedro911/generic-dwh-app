package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;

import java.util.List;

public interface ReferenceObjectService extends CrudService<ReferenceObject, Long>  {

    List<ReferenceObject> findByOrderByIdAsc();
}
