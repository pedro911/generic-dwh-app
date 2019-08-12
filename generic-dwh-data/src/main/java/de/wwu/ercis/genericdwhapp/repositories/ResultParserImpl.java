package de.wwu.ercis.genericdwhapp.repositories;

import de.wwu.ercis.genericdwhapp.model.starschema.FactResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ResultParserImpl implements ResultParser {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FactResult> starQuery(String q, String dimension, String ratio) {
        List<Object[]> objects = em.createNativeQuery(q).getResultList();
        List<FactResult> factResults = new ArrayList<>();

        switch (dimension){
            case "r_name": dimension = "Region";
                break;
            case "n_name": dimension = "Nation";
                break;
            case "c_mktsegment": dimension = "Market Segment";
                break;
            case "c_name": dimension = "Customer Name";
                break;
            default: dimension = "Dimension Not Found";
                break;
        }

        switch (ratio){
            case "l_extendedprice": ratio = "Product Price";
                break;
            case "l_quantity": ratio = "Purchase Amount";
                break;
        }

        for (Object[] obj :objects){
            FactResult factResult = new FactResult();
            factResult.setRatio(ratio);
            factResult.setDimension(dimension);
            factResult.setName(String.valueOf(obj[0]));
            factResult.setValue(Double.valueOf(String.valueOf(obj[1])));
            factResults.add(factResult);
        }

        //facts = em.createNativeQuery(query).setHint(QueryHints.RESULT_TYPE, Fact.class).getResultList();

/*
        Map<String, Object> map = em.createNativeQuery(query).setHint(query, Fact.class).getResultList();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                // Do something with entry.getKey() and entry.getValue()
                System.out.println(entry.getKey());
                referenceObject.setId(Long.parseLong(entry.getKey()));
                fact.setReferenceObject(referenceObject);
                facts.add(fact);
            } else if (entry.getValue() instanceof Class) {
                // Do something else with entry.getKey() and entry.getValue()
                System.out.println(entry.getValue());

            } else {
                throw new IllegalStateException("Expecting either String or Class as entry value");
            }
        }
*/

        return factResults;
    }


}
