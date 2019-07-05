package de.wwu.ercis.genericdwhapp.services.tpchstandard;

import de.wwu.ercis.genericdwhapp.model.tpchstandard.NationEntity;
import de.wwu.ercis.genericdwhapp.services.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NationService extends CrudService<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);


}
