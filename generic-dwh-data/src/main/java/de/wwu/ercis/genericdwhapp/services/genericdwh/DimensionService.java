package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.services.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DimensionService extends CrudService<Dimension, Long> {

    List<Dimension> findByOrderByIdAsc();

    List<Dimension> findAll(Sort sort);

    List<Dimension> findByRoot();

}
