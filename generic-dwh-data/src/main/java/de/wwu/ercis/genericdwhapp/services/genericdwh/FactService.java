package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;

import java.util.List;

public interface FactService extends CrudService<Fact, Long>  {

    List<Fact> findByOrderByIdAsc();
}
