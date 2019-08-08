package de.wwu.ercis.genericdwhapp.services.starchema.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.starschema.FactResult;
import de.wwu.ercis.genericdwhapp.model.starschema.StarSchemaFact;
import de.wwu.ercis.genericdwhapp.repositories.starschema.StarSchemaFactRepository;
import de.wwu.ercis.genericdwhapp.services.starchema.StarSchemaFactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StarSchemaStarSchemaFactSDJpaService implements StarSchemaFactService {

    private final StarSchemaFactRepository starSchemaFactRepository;

    public StarSchemaStarSchemaFactSDJpaService(StarSchemaFactRepository starSchemaFactRepository) {
        this.starSchemaFactRepository = starSchemaFactRepository;
    }

    @Override
    public List<StarSchemaFact> findAll() {
        List<StarSchemaFact> starSchemaFacts = new ArrayList<>();
        starSchemaFactRepository.findAll().forEach(starSchemaFacts::add);
        return starSchemaFacts;
    }

    @Override
    public List<StarSchemaFact> findAllSort(Sort sort) {
        List<StarSchemaFact> starSchemaFacts = new ArrayList<>();
        starSchemaFactRepository.findAll(sort).forEach(starSchemaFacts::add);
        return starSchemaFacts;
    }

    @Override
    public StarSchemaFact findById(Long aLong) {
        return starSchemaFactRepository.findById(aLong).orElse(null);
    }

    @Override
    public List<FactResult> facts(List<String> dimensions, List<String> ratios) {
        List<FactResult> factResults = new ArrayList<>();

        for (String ratio: ratios){
            for (String dimension: dimensions) {
                String query = "SELECT " + dimension + " as 'name', SUM(" + ratio + ") as 'value' FROM dim_customer c\n" +
                        "INNER JOIN fact f ON f.FK_CUSTOMER = c.PK_CUSTKEY\n" +
                        "GROUP BY " + dimension + " ";
                starSchemaFactRepository.starQuery(query,dimension,ratio).forEach(factResults::add);
            }
        }
        return factResults;
    }

}
