package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObjectCombination;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface ReferenceObjectCombinationService extends GenericDWHService<ReferenceObjectCombination, Long> {

    List<ReferenceObjectCombination> findByOrderByCombinationIdAsc();

    List<ReferenceObjectCombination> findAllBySubordinateId(Long id);
}
