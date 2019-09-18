package de.wwu.ercis.genericdwhapp.services.stats.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.stats.QueryString;
import de.wwu.ercis.genericdwhapp.repositories.stats.QueryStringRepository;
import de.wwu.ercis.genericdwhapp.services.stats.QueryStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class QueryStringSDJpaService implements QueryStringService {

    private final QueryStringRepository queryStringRepository;

    public QueryStringSDJpaService(QueryStringRepository queryStringRepository) {
        this.queryStringRepository = queryStringRepository;
    }

    @Override
    public Set<QueryString> findAll() {
        Set<QueryString> queryStrings = new HashSet<>();
        queryStringRepository.findAll().forEach(queryStrings::add);
        return queryStrings;
    }

    @Override
    public QueryString findById(Long aLong) {
        return queryStringRepository.findById(aLong).orElse(null);
    }

    @Override
    public QueryString save(QueryString object) {
        return queryStringRepository.save(object);
    }

    @Override
    public void delete(QueryString object) {
        queryStringRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        queryStringRepository.deleteById(aLong);
    }

    @Override
    public List<QueryString> findAllOrderById() {
        List<QueryString> queryStrings = new ArrayList<>();
        queryStringRepository.findAllByOrderByQueryStringId().forEach(queryStrings::add);
        return queryStrings;
    }
}
