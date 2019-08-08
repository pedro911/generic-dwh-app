package de.wwu.ercis.genericdwhapp.repositories;

import de.wwu.ercis.genericdwhapp.model.starschema.FactResult;

import java.util.List;

public interface ResultParser {

    List<FactResult> starQuery(String query, String dimension, String ratio);

}
