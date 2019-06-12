package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectCombination;

import java.util.List;

public interface ReferenceObjectCombinationService extends CrudService<ReferenceObjectCombination, Long>  {

    List<ReferenceObjectCombination> findByOrderByCombinationIdAsc();
}
