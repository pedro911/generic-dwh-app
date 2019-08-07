package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioCombination;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.util.List;

public interface RatioCombinationService extends GenericDWHService<RatioCombination, Long> {

    List<RatioCombination> findByOrderByCombinationIdAsc();
}
