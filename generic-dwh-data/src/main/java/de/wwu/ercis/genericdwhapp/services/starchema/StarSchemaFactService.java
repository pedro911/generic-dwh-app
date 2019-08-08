package de.wwu.ercis.genericdwhapp.services.starchema;

import de.wwu.ercis.genericdwhapp.model.starschema.FactResult;
import de.wwu.ercis.genericdwhapp.model.starschema.StarSchemaFact;
import de.wwu.ercis.genericdwhapp.services.FindService;

import java.util.List;

public interface StarSchemaFactService extends FindService<StarSchemaFact, Long> {

    List<FactResult> facts(List<String> dimensions, List<String> ratios);

}
