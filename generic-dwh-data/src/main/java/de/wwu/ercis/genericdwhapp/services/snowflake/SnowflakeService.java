package de.wwu.ercis.genericdwhapp.services.snowflake;

import java.util.List;

public interface SnowflakeService {

    List<String[]> snowFacts(List<String> dimensions, List<String> ratios);

    String query();

    List<String[]> adHocQuery(String query);

    List<String> getReferenceObjects(String dimensionId);

}
