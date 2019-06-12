package de.wwu.ercis.genericdwhapp.services.standard.onegb;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import de.wwu.ercis.genericdwhapp.services.standard.CrudService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NationOnegbService extends CrudService<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);

}
