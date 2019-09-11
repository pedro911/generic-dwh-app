package de.wwu.ercis.genericdwhapp.services.snowflake.springdatajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SnowflakeService implements de.wwu.ercis.genericdwhapp.services.snowflake.SnowflakeService {

    @Override
    public List<String[]> snowFacts(List<String> dimensions, List<String> ratios) {
        return null;
    }

    @Override
    public String query() {

        return "test query";
    }
}
