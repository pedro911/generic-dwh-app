package de.wwu.ercis.genericdwhapp.services.star.springdatajpa;

import de.wwu.ercis.genericdwhapp.repositories.starschema.StarRepository;
import de.wwu.ercis.genericdwhapp.services.star.StarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class StarSDJpaService implements StarService {

    private final StarRepository starRepository;
    private String executedQuery ="";

    public StarSDJpaService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    @Override
    public List<String[]> starFacts(List<String> dimensions, List<String> ratios, List<String> filters) {

        List<String> joins = new ArrayList<>();
        String where = "";
        List<String> filters_where = new ArrayList<>();

        if (filters != null) {
            where = "\nWHERE ";
            Map<String, String> filtersMap = filters
                    .stream()
                    .distinct()
                    .map(s -> s.split("%%"))
                    .collect(toMap(s -> s[0], s -> s[1]));

            filtersMap.values().stream().distinct().forEach( dimensionId -> {

                List<String> roIds = filtersMap
                        .entrySet()
                        .stream()
                        .filter(x -> dimensionId.equals(x.getValue()))
                        .map(x -> x.getKey())
                        .collect(Collectors.toList());

                filters_where.add(dimensionId + " IN ('" + roIds.stream().collect(Collectors.joining("','"))+"') ");

                if(!dimensions.contains(dimensionId))
                    dimensions.add(dimensionId);
            });
        }

        if (dimensions.contains("d_month_number") && !dimensions.contains("d_year_number"))
            dimensions.add("d_year_number");

        for (String d: dimensions){
            if (d.startsWith("o") && !joins.stream().anyMatch(s -> s.contains("dim_clerk")))
                joins.add("INNER JOIN dim_clerk k ON k.PK_CLERK = f.FK_CLERK");

            if (d.startsWith("c") && !joins.stream().anyMatch(s -> s.contains("dim_customer")))
                joins.add("INNER JOIN dim_customer c ON c.PK_CUSTOMER = f.FK_CUSTOMER");

            if (d.startsWith("p") && !joins.stream().anyMatch(s -> s.contains("dim_product")))
                joins.add("INNER JOIN dim_product p ON p.PK_PART = f.FK_PART");

            if (d.startsWith("s") && !joins.stream().anyMatch(s -> s.contains("dim_supplier")))
                joins.add("INNER JOIN dim_supplier s ON s.PK_SUPPLIER = f.FK_SUPPLIER");

            if (d.startsWith("d") && !joins.stream().anyMatch(s -> s.contains("dim_date")))
                joins.add("INNER JOIN dim_date d ON d.DATE_PK = f.FK_ORDERDATE");

        }

        String query = "SELECT " + dimensions.stream().collect(Collectors.joining(",")) + ", "
                + ratios.stream().map(r-> "FORMAT(sum("+ r +"),2)").collect(Collectors.joining(","))
                + " FROM fact f\n"
                + joins.stream().collect(Collectors.joining("\n"))
                + where + filters_where.stream().collect(Collectors.joining(" AND "))
                + "\n GROUP BY " + dimensions.stream().collect(Collectors.joining(","))
                + " WITH ROLLUP\n ORDER BY " + dimensions.stream().collect(Collectors.joining(","))
                + " LIMIT 1000";
        executedQuery = query;
        return starRepository.nativeQuery(query);
    }

    @Override
    public List<String> getReferenceObjects(String dimensionId){
        String query = "";
        List<String> result = new ArrayList<>();

        if (dimensionId.startsWith("o"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_clerk ORDER BY " + dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("c"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_customer ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("p"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_product ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("s"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_supplier ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("d"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_date ORDER BY "+ dimensionId + " LIMIT 1000;";

        List<String[]> queryResult = starRepository.nativeQuery(query);
        queryResult.forEach(s -> result.add(s[0].toUpperCase()));

        return result;
    }

    @Override
    public String query() {
        String result = executedQuery.toUpperCase().replaceAll("\n","<br/>");
        result = result.replace("SELECT","<a class=\"text-primary font-weight-bold\">SELECT</a>");
        result = result.replace("FROM","<a class=\"text-danger font-weight-bold\">FROM</a>");
        result = result.replace("INNER JOIN","<a class=\"text-primary font-weight-bold\">INNER JOIN</a>");
        result = result.replace("WHERE","<a class=\"text-danger font-weight-bold\">WHERE</a>");
        result = result.replace("GROUP BY","<a class=\"text-danger font-weight-bold\">GROUP BY</a>");
        result = result.replace("ORDER BY","<a class=\"text-danger font-weight-bold\">ORDER BY</a>");
        result = result.replace("WITH ROLLUP","<a class=\"text-success font-weight-bold\">WITH ROLLUP</a>");
        return result;
    }

    @Override
    public List<String[]> adHocQuery(String query) {
        executedQuery = query;
        return starRepository.nativeQuery(query);
    }

}
