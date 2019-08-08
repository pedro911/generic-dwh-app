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
        List<Object[]> objects = new ArrayList<>();
        objects = em.createNativeQuery(q).getResultList();
        List<FactResult> factResults = new ArrayList<>();

        if (dimension.equals("r_name")) dimension = "Region";
        if (dimension.equals("n_name")) dimension = "Nation";

        for (Object[] obj :objects){
            FactResult factResult = new FactResult();
            factResult.setRatio(ratio);
            factResult.setDimension(dimension);
            factResult.setName(String.valueOf(obj[0]));
            factResult.setValue(Double.valueOf(String.valueOf(obj[1])));
            factResults.add(factResult);
        }

/*        for (int i=0; i<factResults.size(); i++){
            Object[] row = factResults.get(i);
            row = append(row, dimension);
            row = append(row, ratio);
            System.out.println("Element "+i+ Arrays.toString(row));
        }*/


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

/*    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }*/


}
