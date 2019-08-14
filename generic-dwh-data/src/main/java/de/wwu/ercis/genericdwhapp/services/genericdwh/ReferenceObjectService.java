package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface ReferenceObjectService extends GenericDWHService<ReferenceObject, Long> {

    List<ReferenceObject> findByOrderByIdAsc();

    List<ReferenceObject> findAllByDimensionIn(Dimension dimension);

    List<ReferenceObject> findAllByNameContaining(String name);

    List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name);

    ReferenceObject findFirstByDimension(Dimension dimension);

}
