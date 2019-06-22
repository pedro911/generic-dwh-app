package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionCombination;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface DimensionCombinationService extends CrudService<DimensionCombination, Long> {

    List<DimensionCombination> findByOrderByCombinationIdAsc();

}
