package de.wwu.ercis.genericdwhapp.repositories;

import de.wwu.ercis.genericdwhapp.model.Dimension;
import de.wwu.ercis.genericdwhapp.model.DimensionHierarchy;
import org.springframework.data.repository.CrudRepository;

public interface DimensionHierarchyRepository extends CrudRepository<DimensionHierarchy, Long> {

    DimensionHierarchy findByParent(Dimension dimension);

}
