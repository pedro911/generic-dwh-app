package de.wwu.ercis.genericdwhapp.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class ResultParserImpl implements ResultParser {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Object[]> starQuery(String query) {
        return em.createNativeQuery(query).getResultList();
    }


}
