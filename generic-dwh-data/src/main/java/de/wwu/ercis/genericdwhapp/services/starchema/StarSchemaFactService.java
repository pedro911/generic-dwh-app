package de.wwu.ercis.genericdwhapp.services.starchema;

import java.util.List;

public interface StarSchemaFactService {

    List<Object[]> starFacts(List<String> dimensions, List<String> ratios);

    String query();

}
