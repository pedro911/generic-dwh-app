package de.wwu.ercis.genericdwhapp.services.standard;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.services.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NationService extends CrudService<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);


}
