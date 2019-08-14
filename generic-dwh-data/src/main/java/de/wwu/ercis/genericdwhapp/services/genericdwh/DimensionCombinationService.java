package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.DimensionCombination;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface DimensionCombinationService extends GenericDWHService<DimensionCombination, Long> {

    List<DimensionCombination> findByOrderByCombinationIdAsc();

    List<Dimension> findDimensionsByCombinationId();

}
