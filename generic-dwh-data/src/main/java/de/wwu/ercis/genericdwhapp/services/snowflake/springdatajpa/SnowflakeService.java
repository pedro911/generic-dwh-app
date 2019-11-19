package de.wwu.ercis.genericdwhapp.services.snowflake.springdatajpa;

import de.wwu.ercis.genericdwhapp.repositories.snowflakeschema.SnowflakeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SnowflakeService implements de.wwu.ercis.genericdwhapp.services.snowflake.SnowflakeService {

    private final SnowflakeRepository snowflakeRepository;

    private String executedQuery ="";

    public SnowflakeService(SnowflakeRepository snowflakeRepository) {
        this.snowflakeRepository = snowflakeRepository;
    }

    @Override
    public List<String[]> snowFacts(List<String> dimensions, List<String> ratios) {

        List<String> joins = new ArrayList<>();

        if (dimensions.contains("d_month_number") && !dimensions.contains("d_year_number"))
            dimensions.add("d_year_number");

        for (String d: dimensions){
            //Customer Dimension
            if((d.startsWith("r") || d.startsWith("n") || d.startsWith("m") || d.startsWith("c")) && !joins.stream().anyMatch(s -> s.contains("dim_customer")))
                joins.add("INNER JOIN dim_customer c ON c.PK_CUSTOMER = f.FK_CUSTOMER");
            if(d.contains("m_mktsegment"))
                joins.add("INNER JOIN mktsegment m ON m.mktsegment_id = c.mktsegment_id");
            if(d.contains("n_name") && !joins.stream().anyMatch(s -> s.contains("nation")))
                joins.add("INNER JOIN nation n on n.n_nationkey = c.c_nationkey");
            if(d.contains("r_name")){
                joins.add("INNER JOIN nation n on n.n_nationkey = c.c_nationkey");
                joins.add("INNER JOIN region r on r.r_regionkey = n.n_regionkey");
            }

            //Clerk Dimension
            if(d.startsWith("o") && !joins.stream().anyMatch(s -> s.contains("dim_clerk")))
                joins.add("INNER JOIN dim_clerk k ON k.PK_CLERK = f.FK_CLERK");

            //Product Dimension
            if (d.startsWith("p")  && !joins.stream().anyMatch(s -> s.contains("dim_part")))
                joins.add("INNER JOIN dim_part p ON p.PK_PART = f.FK_PART");
            if(d.contains("p_mfgr")){
                joins.add("INNER JOIN p_brand pb on pb.p_brand_id = p.p_brand_id");
                joins.add("INNER JOIN p_mfgr pm on pm.p_mfgr_id = pb.p_mfgr_id");
            }
            if(d.contains("p_brand") && !joins.stream().anyMatch(s -> s.contains("p_brand")))
                joins.add("INNER JOIN p_brand pb on pb.p_brand_id = p.p_brand_id");
            if(d.contains("p_type"))
                joins.add("INNER JOIN p_type pt on pt.p_type_id = p.p_type_id");


            //Supplier Dimension
            if (d.startsWith("s") && !joins.stream().anyMatch(s -> s.contains("dim_supplier")))
                joins.add("INNER JOIN dim_supplier s ON s.PK_SUPPLIER = f.FK_SUPPLIER");

            //Date Dimension
            if (d.startsWith("d") && !joins.stream().anyMatch(s -> s.contains("dim_date")))
                joins.add("INNER JOIN dim_date d ON d.DATE_PK = f.FK_ORDERDATE");

        }

        String query = "SELECT " + dimensions.stream().collect(Collectors.joining(",")) + ", "
                + ratios.stream().map(r-> "FORMAT(sum("+ r +"),2)").collect(Collectors.joining(","))
                + " FROM fact f\n"
                + joins.stream().collect(Collectors.joining("\n"))
                + "\n GROUP BY " + dimensions.stream().collect(Collectors.joining(","))
                + " WITH ROLLUP\n ORDER BY " + dimensions.stream().collect(Collectors.joining(","));
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
}
