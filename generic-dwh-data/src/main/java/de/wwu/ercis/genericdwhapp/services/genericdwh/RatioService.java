package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;

import java.util.List;

public interface RatioService extends CrudService<Ratio, Long>  {

    List<Ratio> findByOrderByIdAsc();
}
