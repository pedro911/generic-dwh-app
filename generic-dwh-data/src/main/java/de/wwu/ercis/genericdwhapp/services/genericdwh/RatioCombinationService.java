package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.RatioCombination;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface RatioCombinationService extends CrudService<RatioCombination, Long> {

    List<RatioCombination> findByOrderByCombinationIdAsc();
}
