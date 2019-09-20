package de.wwu.ercis.genericdwhapp.services.stats;

import de.wwu.ercis.genericdwhapp.model.stats.QueryTime;
import de.wwu.ercis.genericdwhapp.services.CrudService;

import java.util.List;
import java.util.Set;

public interface QueryTimeService extends CrudService<QueryTime, Long> {

    Set<QueryTime> findByDbModelContaining(String dbName);

    void addQueryTime(String db, String refObj_and_Ratio, Double time_ms);

    void savePending();

    List<String[]> top5slowestQueries(String dbSize, String _dbModel);

    List<String[]> labels(String dbSize);

    List<String[]> countQueries(String _dbModel);

}
