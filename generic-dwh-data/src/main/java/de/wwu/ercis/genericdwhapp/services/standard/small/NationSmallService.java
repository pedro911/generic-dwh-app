package de.wwu.ercis.genericdwhapp.services.standard.small;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.services.standard.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NationSmallService extends CrudService<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);


}
