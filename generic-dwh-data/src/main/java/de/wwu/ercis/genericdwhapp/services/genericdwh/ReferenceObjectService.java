package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface ReferenceObjectService extends CrudService<ReferenceObject, Long> {

    List<ReferenceObject> findByOrderByIdAsc();

    List<ReferenceObject> findAllByDimensionIn(Dimension dimension);

    List<ReferenceObject> findAllByNameContaining(String name);

    List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name);

    ReferenceObject findFirstByDimension(Dimension dimension);

}
