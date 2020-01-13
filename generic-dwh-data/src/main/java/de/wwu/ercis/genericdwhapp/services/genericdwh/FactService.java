package de.wwu.ercis.genericdwhapp.services.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.services.GenericDWHService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FactService extends GenericDWHService<Fact, Long> {

    String queryMethod();

    String query();

    List<String[]> gdwhDynQuery(List<String> ratios, List<String> dimensions) throws IOException, URISyntaxException;

    List<String[]> gdwhAcbQuery(List<String> ratios, List<String> dimensions);

    List<String[]> gdwhNcbQuery(List<String> ratios, List<String> dimensions);

}
