package de.wwu.ercis.genericdwhapp.services;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.HashMap;
import java.util.Map;

public class AllDBMasterService {
    public static Map<Object, Object> getDataSourceHashMap() {

        DriverManagerDataSource genericdwh_slides = new DriverManagerDataSource();
        genericdwh_slides.setUrl("jdbc:mysql://localhost:3306/genericdwh_slides");
        genericdwh_slides.setUsername("root");
        genericdwh_slides.setPassword("root");

        DriverManagerDataSource genericdwh_small = new DriverManagerDataSource();
        genericdwh_small.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small");
        genericdwh_small.setUsername("root");
        genericdwh_small.setPassword("root");

        DriverManagerDataSource tpch_std_small = new DriverManagerDataSource();
        tpch_std_small.setUrl("jdbc:mysql://localhost:3306/tpch_std_small");
        tpch_std_small.setUsername("root");
        tpch_std_small.setPassword("root");

        DriverManagerDataSource tpch_std_onegb = new DriverManagerDataSource();
        tpch_std_onegb.setUrl("jdbc:mysql://localhost:3306/tpch1");
        tpch_std_onegb.setUsername("root");
        tpch_std_onegb.setPassword("root");

        //TODO
        //add other dbs here

        HashMap hashMap = new HashMap();
        hashMap.put("tpch_std_onegb", tpch_std_onegb);
        hashMap.put("tpch_std_small", tpch_std_small);
        hashMap.put("genericdwh_slides", genericdwh_slides);
        hashMap.put("genericdwh_small", genericdwh_small);
        return hashMap;

    }
}