package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ResultParserImpl implements ResultParser {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Fact> findSpecial(String q) {
        List<Fact> facts = new ArrayList<>();
        Fact fact = new Fact();
        ReferenceObject referenceObject = new ReferenceObject();
        TypedQuery<Fact> query = em.createQuery(q,Fact.class);

        log.debug("print results");
        System.out.println(query.getResultList());

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

        return query.getResultList();
    }


}
