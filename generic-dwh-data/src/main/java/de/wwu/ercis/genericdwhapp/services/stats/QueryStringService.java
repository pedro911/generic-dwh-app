package de.wwu.ercis.genericdwhapp.services.stats;

import de.wwu.ercis.genericdwhapp.model.stats.QueryString;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface QueryStringService extends CrudService<QueryString, Long> {

    List<QueryString> findAllOrderById();

}
