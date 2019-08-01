package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;

public interface FactService extends CrudService<Fact, Long> {

    Fact findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio);

    List<Fact> queryResults(List<String> ratios, List<String> dimensions, List<String> dCombinations);

    String queryMethod();

    List<Fact> findByDimensionIdAndRatioId(String dimensionId, String ratioId);

    List<Fact> genericDWHResults(String dimensionId, String ratioId, String dimensionCombination);

    List<Fact> findSpecial();

}
