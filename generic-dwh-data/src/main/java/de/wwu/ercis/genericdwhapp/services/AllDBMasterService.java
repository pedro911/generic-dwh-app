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

        DriverManagerDataSource tpch_gdwh_mini_dyn = new DriverManagerDataSource();
        tpch_gdwh_mini_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_dyn");
        tpch_gdwh_mini_dyn.setUsername(user);
        tpch_gdwh_mini_dyn.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_mini_ncb = new DriverManagerDataSource();
        tpch_gdwh_mini_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_ncb");
        tpch_gdwh_mini_ncb.setUsername(user);
        tpch_gdwh_mini_ncb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_mini_acb = new DriverManagerDataSource();
        tpch_gdwh_mini_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_acb");
        tpch_gdwh_mini_acb.setUsername(user);
        tpch_gdwh_mini_acb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_small_dyn = new DriverManagerDataSource();
        tpch_gdwh_small_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_dyn");
        tpch_gdwh_small_dyn.setUsername(user);
        tpch_gdwh_small_dyn.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_small_ncb = new DriverManagerDataSource();
        tpch_gdwh_small_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_ncb");
        tpch_gdwh_small_ncb.setUsername(user);
        tpch_gdwh_small_ncb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_small_acb = new DriverManagerDataSource();
        tpch_gdwh_small_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_acb");
        tpch_gdwh_small_acb.setUsername(user);
        tpch_gdwh_small_acb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_1gb_dyn = new DriverManagerDataSource();
        tpch_gdwh_1gb_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_dyn");
        tpch_gdwh_1gb_dyn.setUsername(user);
        tpch_gdwh_1gb_dyn.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_1gb_ncb = new DriverManagerDataSource();
        tpch_gdwh_1gb_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_ncb");
        tpch_gdwh_1gb_ncb.setUsername(user);
        tpch_gdwh_1gb_ncb.setPassword(pass);

        DriverManagerDataSource tpch_gdwh_1gb_acb = new DriverManagerDataSource();
        tpch_gdwh_1gb_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_acb");
        tpch_gdwh_1gb_acb.setUsername(user);
        tpch_gdwh_1gb_acb.setPassword(pass);

        DriverManagerDataSource tpch_star_mini = new DriverManagerDataSource();
        tpch_star_mini.setUrl("jdbc:mysql://localhost:3306/tpch_star_mini");
        tpch_star_mini.setUsername(user);
        tpch_star_mini.setPassword(pass);

        DriverManagerDataSource tpch_star_small = new DriverManagerDataSource();
        tpch_star_small.setUrl("jdbc:mysql://localhost:3306/tpch_star_small");
        tpch_star_small.setUsername(user);
        tpch_star_small.setPassword(pass);

        //TODO
        //add other dbs here

        HashMap hashMap = new HashMap();
        hashMap.put("genericdwh_slides", genericdwh_slides);

        hashMap.put("tpch_std_mini", tpch_std_mini);
        hashMap.put("tpch_std_onegb", tpch_std_onegb);

        hashMap.put("tpch_gdwh_mini_dyn", tpch_gdwh_mini_dyn);
        hashMap.put("tpch_gdwh_mini_ncb", tpch_gdwh_mini_ncb);
        hashMap.put("tpch_gdwh_mini_acb", tpch_gdwh_mini_acb);

        hashMap.put("tpch_gdwh_small_dyn", tpch_gdwh_small_dyn);
        hashMap.put("tpch_gdwh_small_ncb", tpch_gdwh_small_ncb);
        hashMap.put("tpch_gdwh_small_acb", tpch_gdwh_small_acb);

        hashMap.put("tpch_gdwh_1gb_dyn", tpch_gdwh_1gb_dyn);
        hashMap.put("tpch_gdwh_1gb_ncb", tpch_gdwh_1gb_ncb);
        hashMap.put("tpch_gdwh_1gb_acb", tpch_gdwh_1gb_acb);

        hashMap.put("tpch_star_mini", tpch_star_mini);
        hashMap.put("tpch_star_small", tpch_star_small);

        return hashMap;

    }
}