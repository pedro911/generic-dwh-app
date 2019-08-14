package de.wwu.ercis.genericdwhapp.repositories;

import java.util.List;

public interface ResultParser {

    List<Object[]> starQuery(String query);

}
