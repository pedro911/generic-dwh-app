package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DimensionService extends GenericDWHService<Dimension, Long> {

    List<Dimension> findByOrderByIdAsc();

    List<Dimension> findAll(Sort sort);

    List<Dimension> findByRoot();

    Dimension findByName(String name);

    List<Dimension> findAtomicAndHierarchyLevels();

}
