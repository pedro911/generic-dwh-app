package de.wwu.ercis.genericdwhapp.services;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.HashMap;
import java.util.Map;

public class AllDBMasterService {
    public static Map<Object, Object> getDataSourceHashMap() {

        final String user = "root";
        final String pass = "root";

        DriverManagerDataSource genericdwh_slides = new DriverManagerDataSource();
        genericdwh_slides.setUrl("jdbc:mysql://localhost:3306/genericdwh_slides");
        genericdwh_slides.setUsername(user);
        genericdwh_slides.setPassword(pass);

        DriverManagerDataSource tpch_std_mini = new DriverManagerDataSource();
        tpch_std_mini.setUrl("jdbc:mysql://localhost:3306/tpch_std_mini");
        tpch_std_mini.setUsername(user);
        tpch_std_mini.setPassword(pass);

        DriverManagerDataSource tpch_std_onegb = new DriverManagerDataSource();
        tpch_std_onegb.setUrl("jdbc:mysql://localhost:3306/tpch1");
        tpch_std_onegb.setUsername(user);
        tpch_std_onegb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_mini = new DriverManagerDataSource();
        tpch_gdwh_mini.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini");
        tpch_gdwh_mini.setUsername(user);
        tpch_gdwh_mini.setPassword(pass);

        DriverManagerDataSource tpch_star_mini = new DriverManagerDataSource();
        tpch_star_mini.setUrl("jdbc:mysql://localhost:3306/tpch_star_mini");
        tpch_star_mini.setUsername(user);
        tpch_star_mini.setPassword(pass);

        //TODO
        //add other dbs here

        HashMap hashMap = new HashMap();
        hashMap.put("genericdwh_slides", genericdwh_slides);

        hashMap.put("tpch_std_mini", tpch_std_mini);
        hashMap.put("tpch_std_onegb", tpch_std_onegb);

        hashMap.put("tpch_gdwh_mini", tpch_gdwh_mini);

        hashMap.put("tpch_star_mini", tpch_star_mini);

        return hashMap;

    }
}