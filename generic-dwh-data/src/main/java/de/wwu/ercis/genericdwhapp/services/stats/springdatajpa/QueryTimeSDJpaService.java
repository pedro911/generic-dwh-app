package de.wwu.ercis.genericdwhapp.services.stats.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.stats.DBModel;
import de.wwu.ercis.genericdwhapp.model.stats.QueryString;
import de.wwu.ercis.genericdwhapp.model.stats.QueryTime;
import de.wwu.ercis.genericdwhapp.repositories.stats.DBModelRepository;
import de.wwu.ercis.genericdwhapp.repositories.stats.QueryStringRepository;
import de.wwu.ercis.genericdwhapp.repositories.stats.QueryTimeRepository;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class QueryTimeSDJpaService implements QueryTimeService {

    private final QueryTimeRepository queryTimeRepository;
    private final DBModelRepository dbModelRepository;
    private final QueryStringRepository queryStringRepository;
    private Set<QueryTime> queryTimeList = new HashSet<>();
    private DBModel dbModel;
    private QueryString queryString;
    private QueryTime queryTime;

    public QueryTimeSDJpaService(QueryTimeRepository queryTimeRepository, DBModelRepository dbModelRepository,
                                 QueryStringRepository queryStringRepository) {
        this.queryTimeRepository = queryTimeRepository;
        this.dbModelRepository = dbModelRepository;
        this.queryStringRepository = queryStringRepository;
    }

    @Override
    public Set<QueryTime> findAll() {
        Set<QueryTime> queryTimes = new HashSet<>();
        queryTimeRepository.findAll().forEach(queryTimes::add);
        return queryTimes;
    }

    @Override
    public QueryTime findById(Long aLong) {
        return queryTimeRepository.findById(aLong).orElse(null);
    }

    @Override
    public QueryTime save(QueryTime object) {
        return queryTimeRepository.save(object);
    }

    @Override
    public void delete(QueryTime object) {
        queryTimeRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        queryTimeRepository.deleteById(aLong);
    }

    @Override
    public Set<QueryTime> findByDbModelContaining(String dbName) {
        Set<DBModel> dbModels = new HashSet<>();
        dbModelRepository.findAllByDbNameContaining(dbName).forEach(dbModels::add);
        Set<QueryTime> queryTimes = new HashSet<>();
        for (DBModel db: dbModels){
            queryTimeRepository.findAllByDbModel(db).forEach(queryTimes::add);
        }

        return queryTimes;
    }

    @Override
    public void addQueryTime(String db, String refObj_and_Ratio, Double time_ms) {
        dbModel = new DBModel(db);
        queryString = new QueryString(refObj_and_Ratio);
        queryTime = new QueryTime(dbModel,queryString,time_ms,new Timestamp(System.currentTimeMillis()));
        queryTimeList.add(queryTime);
    }

    @Override
    public void savePending() {
        log.debug("save pending... ");
        queryTimeList.forEach(q -> System.out.println(q.getQueryString().toString() +", " + q.getQueryTimeMs()));
        for (QueryTime qt :queryTimeList){
            DBModel dbModel1 = dbModelRepository.findDBModelByDbName(qt.getDbModel().getDbName()).orElse(null);
            if (dbModel1 == null){
                dbModel1 =  dbModelRepository.save(qt.getDbModel());
                qt.setDbModel(dbModel1);
            }
            else
                qt.setDbModel(dbModel1);

            QueryString queryString1 = queryStringRepository.findQueryStringByRefObjRatios(qt.getQueryString().getRefObjRatios()).orElse(null);
            if (queryString1 == null){
                queryString1 = queryStringRepository.save(qt.getQueryString());
                qt.setQueryString(queryString1);
            }
            else
                qt.setQueryString(queryString1);

            queryTimeRepository.save(qt);
        }
        queryTimeList.clear();
    }

    @Override
    public List<String[]> smallDB() {
        List<String[]> gendwh_dyn;
        List<String[]> gendwh_ncb;
        List<String[]> gendwh_acb;
        List<String[]> star = new ArrayList<>();
        List<String[]> snow;

        String query = "SELECT qt.query_string_id FROM query_time qt\n" +
                "INNER JOIN db_model d ON d.db_model_id = qt.db_model_id\n" +
                "WHERE d.db_name LIKE '%small%'\n" +
                "GROUP BY qt.query_string_id\n" +
                "ORDER BY AVG(query_time_ms) DESC\n" +
                "LIMIT 5";
        List<String[]> top5slowestQueriesIds = queryStringRepository.nativeQuery(query);

        for (Object[] o: top5slowestQueriesIds){
            log.debug("ids... ");
            System.out.println(o[0]);
            String q2 = "SELECT AVG(qt.query_time_ms), d.db_name FROM query_time qt\n" +
                    "INNER JOIN db_model d ON d.db_model_id = qt.db_model_id\n" +
                    "WHERE d.db_name LIKE '%star_small%' AND qt.query_string_id = " + o[0];
            star.add(queryStringRepository.nativeQuery(q2).get(0));
        }
        System.out.println(star);

        return star;
    }

    @Override
    public List<String[]> labels() {

        String query = "SELECT qt.query_string_id FROM query_time qt\n" +
                "INNER JOIN db_model d ON d.db_model_id = qt.db_model_id\n" +
                "WHERE d.db_name LIKE '%small%'\n" +
                "GROUP BY qt.query_string_id\n" +
                "ORDER BY AVG(query_time_ms) DESC\n" +
                "LIMIT 5";
        List<String[]> top5slowestQueriesIds = queryStringRepository.nativeQuery(query);


        return top5slowestQueriesIds;
    }

}
