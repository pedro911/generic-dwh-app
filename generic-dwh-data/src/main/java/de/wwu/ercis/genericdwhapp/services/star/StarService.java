package de.wwu.ercis.genericdwhapp.services.star;

import java.util.List;

public interface StarService {

    List<String[]> starFacts(List<String> dimensions, List<String> ratios, List<String> filters);

    List<String> getReferenceObjects(String dimensionId);

    String query();

    List<String[]> adHocQuery(String query);

}
