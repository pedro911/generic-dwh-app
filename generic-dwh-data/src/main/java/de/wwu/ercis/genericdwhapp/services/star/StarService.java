package de.wwu.ercis.genericdwhapp.services.star;

import java.util.List;

public interface StarService {

    List<String[]> starFacts(List<String> dimensions, List<String> ratios);

    String query();

}
