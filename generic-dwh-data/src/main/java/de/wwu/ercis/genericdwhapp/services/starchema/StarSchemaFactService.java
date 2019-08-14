package de.wwu.ercis.genericdwhapp.services.starchema;

import java.util.List;

public interface StarSchemaFactService {

    List<Object[]> facts(List<String> dimensions, List<String> ratios);

    String query();

}
