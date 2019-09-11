package de.wwu.ercis.genericdwhapp.services.star.springdatajpa;

import de.wwu.ercis.genericdwhapp.repositories.starschema.StarRepository;
import de.wwu.ercis.genericdwhapp.services.star.StarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StarSDJpaService implements StarService {

    private final StarRepository starRepository;
    private String executedQuery ="";

    public StarSDJpaService(StarRepository starRepository) {
        this.starRepository = starRepository;
    }

    @Override
    public List<String[]> starFacts(List<String> dimensions, List<String> ratios) {
        String query = "";
        List<String> selects = new ArrayList<>();
        String from = " FROM fact f\n";
        String joins = "";
        List<String> groupBy = new ArrayList<>();

        for (String d: dimensions){
            if (d.startsWith("o") || d.startsWith("l")){
                if (!joins.contains("dim_lineorder"))
                    joins = joins + "INNER JOIN dim_lineorder l ON l.PK_LINEORDER = f.FK_LINEORDER\n";
                groupBy.add(d);
                selects.add(d);
            }
            else if (d.startsWith("c")){
                if (!joins.contains("dim_customer"))
                    joins = joins + "INNER JOIN dim_customer c ON c.PK_CUSTKEY = f.FK_CUSTOMER\n";;
                groupBy.add(d);
                selects.add(d);
            }
            else if (d.startsWith("p")){
                if (!joins.contains("dim_part"))
                    joins = joins +  "INNER JOIN dim_part p ON p.PK_PARTKEY = f.FK_PART\n";;
                groupBy.add(d);
                selects.add(d);
            }
            else if (d.startsWith("d")){
                if (!joins.contains("dim_date"))
                    joins = joins +  "INNER JOIN dim_date d ON d.DATE_PK = f.FK_ORDERDATE\n";;
                groupBy.add(d);
                selects.add(d);
            }
        }

        query = "SELECT " + selects.stream().collect(Collectors.joining(",")) + ", "
                + ratios.stream().map(r-> "FORMAT(sum("+ r +"),2)").collect(Collectors.joining(","))
                + from + joins
                + "GROUP BY " + groupBy.stream().collect(Collectors.joining(","))
                + " WITH ROLLUP\n ORDER BY " + groupBy.stream().collect(Collectors.joining(","));

        executedQuery = query;
        return starRepository.nativeQuery(query);
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

}
