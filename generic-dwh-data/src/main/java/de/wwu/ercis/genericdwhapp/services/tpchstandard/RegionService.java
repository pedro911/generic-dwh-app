package de.wwu.ercis.genericdwhapp.services.tpchstandard;

import de.wwu.ercis.genericdwhapp.model.tpchstandard.RegionEntity;
import de.wwu.ercis.genericdwhapp.services.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RegionService extends CrudService<RegionEntity, Long> {

    List<RegionEntity> findAll(Sort sort);
}
