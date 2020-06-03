package de.wwu.ercis.genericdwhapp.services.snowflake.springdatajpa;

import de.wwu.ercis.genericdwhapp.repositories.snowflakeschema.SnowflakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class SnowflakeService implements de.wwu.ercis.genericdwhapp.services.snowflake.SnowflakeService {

    private final SnowflakeRepository snowflakeRepository;

    private String executedQuery ="";

    public SnowflakeService(SnowflakeRepository snowflakeRepository) {
        this.snowflakeRepository = snowflakeRepository;
    }

    @Override
    public List<String[]> snowFacts(List<String> dimensions, List<String> ratios, List<String> filters) {

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
            //Customer Dimension
            if((d.contains("r_name") || d.contains("n_name") || d.contains("c_name") || d.contains("market_segment"))
                    && !joins.stream().anyMatch(s -> s.contains("dim_customer")))
                joins.add("INNER JOIN dim_customer c ON c.PK_CUSTOMER = f.FK_CUSTOMER");
            if(d.contains("market_segment"))
                joins.add("INNER JOIN market_segment m ON m.pk_market_segment = c.market_segment_id");
            if(d.contains("n_name") && !joins.stream().anyMatch(s -> s.contains("nation")))
                joins.add("INNER JOIN nation n on n.n_nationkey = c.c_nationkey");
            if(d.contains("r_name")){
                joins.add("INNER JOIN nation n on n.n_nationkey = c.c_nationkey");
                joins.add("INNER JOIN region r on r.r_regionkey = n.n_regionkey");
            }

            //Clerk Dimension
            if(d.contains("o_clerk") && !joins.stream().anyMatch(s -> s.contains("dim_clerk")))
                joins.add("INNER JOIN dim_clerk k ON k.PK_CLERK = f.FK_CLERK");

            //Product Dimension
            if ( (d.startsWith("p") || d.contains("manufacturer_group"))
                    && !joins.stream().anyMatch(s -> s.contains("dim_product")))
                joins.add("INNER JOIN dim_product p ON p.PK_PART = f.FK_PART");
            if(d.contains("manufacturer_group")){
                joins.add("INNER JOIN product_brand pb on pb.pk_product_brand = p.product_brand_id");
                joins.add("INNER JOIN manufacturer_group mg on mg.pk_manufacturer_group = pb.manufacturer_group_id");
            }
            if(d.contains("product_brand") && !joins.stream().anyMatch(s -> s.contains("product_brand")))
                joins.add("INNER JOIN product_brand pb on pb.pk_product_brand = p.product_brand_id");
            if(d.contains("product_type"))
                joins.add("INNER JOIN product_type pt on pt.pk_product_type = p.product_type_id");

            //Supplier Dimension
            if (d.contains("s_name") && !joins.stream().anyMatch(s -> s.contains("dim_supplier")))
                joins.add("INNER JOIN dim_supplier s ON s.PK_SUPPLIER = f.FK_SUPPLIER");

            //Date Dimension
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
        return snowflakeRepository.nativeQuery(query);
    }

    @Override
    public String query() {
        String result = executedQuery.toUpperCase().replaceAll("\n","<br/>");
        result = result.replace("SELECT","<a class=\"text-primary font-weight-bold\">SELECT</a>");
        result = result.replace("FROM FACT F","<a class=\"text-danger font-weight-bold\">FROM FACT F</a>");
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
        return snowflakeRepository.nativeQuery(query);
    }

    @Override
    public List<String> getReferenceObjects(String dimensionId) {
        String query = "";
        List<String> result = new ArrayList<>();

        if (dimensionId.startsWith("o"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_clerk ORDER BY " + dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("s"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_supplier ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.startsWith("d"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_date ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("r_name"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM region ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("n_name"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM nation ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("c_name"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_customer ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("market_segment"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM market_segment ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("manufacturer_group"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM manufacturer_group ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("product_brand"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM product_brand ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("p_name"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM dim_product ORDER BY "+ dimensionId + " LIMIT 1000;";

        else if (dimensionId.equals("product_type"))
            query = "SELECT DISTINCT "+ dimensionId + " FROM product_type ORDER BY "+ dimensionId + " LIMIT 1000;";

        List<String[]> queryResult = snowflakeRepository.nativeQuery(query);
        queryResult.forEach(s -> result.add(s[0].toUpperCase()));

        return result;
    }

}
