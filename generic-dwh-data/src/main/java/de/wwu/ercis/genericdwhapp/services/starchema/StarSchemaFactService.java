package de.wwu.ercis.genericdwhapp.services.starchema;

import java.util.List;

public interface StarSchemaFactService {

    List<String[]> starFacts(List<String> dimensions, List<String> ratios);

    String query();

}
