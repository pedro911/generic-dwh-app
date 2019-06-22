package de.wwu.ercis.genericdwhapp.services.standard;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import de.wwu.ercis.genericdwhapp.services.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RegionService extends CrudService<RegionEntity, Long> {

    List<RegionEntity> findAll(Sort sort);
}
