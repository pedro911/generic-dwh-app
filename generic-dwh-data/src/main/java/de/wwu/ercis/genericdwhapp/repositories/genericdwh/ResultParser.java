package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;

import java.util.List;

public interface ResultParser {

    List<Fact> findSpecial(String query);
}
