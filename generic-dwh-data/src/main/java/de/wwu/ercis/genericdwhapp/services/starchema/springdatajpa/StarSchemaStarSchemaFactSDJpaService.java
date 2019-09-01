package de.wwu.ercis.genericdwhapp.services.starchema.springdatajpa;

import de.wwu.ercis.genericdwhapp.repositories.starschema.StarSchemaFactRepository;
import de.wwu.ercis.genericdwhapp.services.starchema.StarSchemaFactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StarSchemaStarSchemaFactSDJpaService implements StarSchemaFactService {

    private final StarSchemaFactRepository starSchemaFactRepository;
    private String executedQuery ="";

    public StarSchemaStarSchemaFactSDJpaService(StarSchemaFactRepository starSchemaFactRepository) {
        this.starSchemaFactRepository = starSchemaFactRepository;
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
                    joins = joins + "INNER JOIN dim_lineorder l ON l.PK_ORDER = f.FK_ORDER\n";
                groupBy.add(d);
                selects.add(d);
            }
            else if (d.startsWith("c")){
                if (!joins.contains("dim_customer"))
                    joins = joins + "INNER JOIN dim_customer c ON c.PK_CUSTKEY = f.FK_CUSTOMER\n";;
                groupBy.add(d);
                selects.add(d);
            }
            else if (d.startsWith("s")){
                if (!joins.contains("dim_supplier"))
                    joins = joins + "INNER JOIN dim_supplier s ON s.PK_SUPPLIER = f.FK_SUPPLIER\n";;
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
        return starSchemaFactRepository.nativeQuery(query);
    }

    @Override
    public String query() {
        return executedQuery;
    }

}
