package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface FactService extends CrudService<Fact, Long> {

    List<Fact> findByOrderByRatioIdAsc();

    Fact findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio);

    Fact findByReferenceObjectIdAndRatioId(Long roId, Long ratioId);

    Fact findByReferenceObjectId(Long id);

    Fact findFirstByReferenceObjectIdAndRatioId(Long roId, Long ratioId);

}
