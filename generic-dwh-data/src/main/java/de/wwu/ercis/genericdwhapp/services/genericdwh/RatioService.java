package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface RatioService extends CrudService<Ratio, Long> {

    List<Ratio> findByOrderByIdAsc();
}
