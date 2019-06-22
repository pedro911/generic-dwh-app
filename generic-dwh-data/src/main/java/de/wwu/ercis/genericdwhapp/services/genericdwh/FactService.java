package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface FactService extends CrudService<Fact, Long> {

    List<Fact> findByOrderByRatioIdAsc();
}
