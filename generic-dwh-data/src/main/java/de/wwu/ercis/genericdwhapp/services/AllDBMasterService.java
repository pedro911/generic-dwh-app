package de.wwu.ercis.genericdwhapp.services;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.HashMap;
import java.util.Map;

public class AllDBMasterService {
    public static Map<Object, Object> getDataSourceHashMap() {

        HashMap hashMap = new HashMap();
        final String user = "root";
        final String pass = "root";

        //GENERIC DWH SCHEMAS

        DriverManagerDataSource tpch_gdwh_mini_dyn = new DriverManagerDataSource();
        tpch_gdwh_mini_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_dyn");
        tpch_gdwh_mini_dyn.setUsername(user);
        tpch_gdwh_mini_dyn.setPassword(pass);
        hashMap.put("tpch_gdwh_mini_dyn", tpch_gdwh_mini_dyn);

        DriverManagerDataSource tpch_gdwh_mini_ncb = new DriverManagerDataSource();
        tpch_gdwh_mini_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_ncb");
        tpch_gdwh_mini_ncb.setUsername(user);
        tpch_gdwh_mini_ncb.setPassword(pass);
        hashMap.put("tpch_gdwh_mini_ncb", tpch_gdwh_mini_ncb);

        DriverManagerDataSource tpch_gdwh_mini_acb = new DriverManagerDataSource();
        tpch_gdwh_mini_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_mini_acb");
        tpch_gdwh_mini_acb.setUsername(user);
        tpch_gdwh_mini_acb.setPassword(pass);
        hashMap.put("tpch_gdwh_mini_acb", tpch_gdwh_mini_acb);

        DriverManagerDataSource tpch_gdwh_small_dyn = new DriverManagerDataSource();
        tpch_gdwh_small_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_dyn");
        tpch_gdwh_small_dyn.setUsername(user);
        tpch_gdwh_small_dyn.setPassword(pass);
        hashMap.put("tpch_gdwh_small_dyn", tpch_gdwh_small_dyn);

        DriverManagerDataSource tpch_gdwh_small_ncb = new DriverManagerDataSource();
        tpch_gdwh_small_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_ncb");
        tpch_gdwh_small_ncb.setUsername(user);
        tpch_gdwh_small_ncb.setPassword(pass);
        hashMap.put("tpch_gdwh_small_ncb", tpch_gdwh_small_ncb);

        DriverManagerDataSource tpch_gdwh_small_acb = new DriverManagerDataSource();
        tpch_gdwh_small_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_small_acb");
        tpch_gdwh_small_acb.setUsername(user);
        tpch_gdwh_small_acb.setPassword(pass);
        hashMap.put("tpch_gdwh_small_acb", tpch_gdwh_small_acb);

        DriverManagerDataSource tpch_gdwh_sf1_dyn = new DriverManagerDataSource();
        tpch_gdwh_sf1_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_dyn");
        tpch_gdwh_sf1_dyn.setUsername(user);
        tpch_gdwh_sf1_dyn.setPassword(pass);
        hashMap.put("tpch_gdwh_sf1_dyn", tpch_gdwh_sf1_dyn);

        DriverManagerDataSource tpch_gdwh_sf1_ncb = new DriverManagerDataSource();
        tpch_gdwh_sf1_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_ncb");
        tpch_gdwh_sf1_ncb.setUsername(user);
        tpch_gdwh_sf1_ncb.setPassword(pass);
        hashMap.put("tpch_gdwh_sf1_ncb", tpch_gdwh_sf1_ncb);

        DriverManagerDataSource tpch_gdwh_sf1_acb = new DriverManagerDataSource();
        tpch_gdwh_sf1_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_1gb_acb");
        tpch_gdwh_sf1_acb.setUsername(user);
        tpch_gdwh_sf1_acb.setPassword(pass);
        hashMap.put("tpch_gdwh_sf1_acb", tpch_gdwh_sf1_acb);

        DriverManagerDataSource tpch_gdwh_sf10_dyn = new DriverManagerDataSource();
        tpch_gdwh_sf10_dyn.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_10gb_dyn");
        tpch_gdwh_sf10_dyn.setUsername(user);
        tpch_gdwh_sf10_dyn.setPassword(pass);
        hashMap.put("tpch_gdwh_sf10_dyn", tpch_gdwh_sf10_dyn);

        DriverManagerDataSource tpch_gdwh_sf10_ncb = new DriverManagerDataSource();
        tpch_gdwh_sf10_ncb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_10gb_ncb");
        tpch_gdwh_sf10_ncb.setUsername(user);
        tpch_gdwh_sf10_ncb.setPassword(pass);
        hashMap.put("tpch_gdwh_sf10_ncb", tpch_gdwh_sf10_ncb);

        DriverManagerDataSource tpch_gdwh_sf10_acb = new DriverManagerDataSource();
        tpch_gdwh_sf10_acb.setUrl("jdbc:mysql://localhost:3306/tpch_gdwh_10gb_acb");
        tpch_gdwh_sf10_acb.setUsername(user);
        tpch_gdwh_sf10_acb.setPassword(pass);
        hashMap.put("tpch_gdwh_sf10_acb", tpch_gdwh_sf10_acb);

        //STAR SCHEMAS

        DriverManagerDataSource tpch_star_mini = new DriverManagerDataSource();
        tpch_star_mini.setUrl("jdbc:mysql://localhost:3306/tpch_star_mini");
        tpch_star_mini.setUsername(user);
        tpch_star_mini.setPassword(pass);
        hashMap.put("tpch_star_mini", tpch_star_mini);

        DriverManagerDataSource tpch_star_small = new DriverManagerDataSource();
        tpch_star_small.setUrl("jdbc:mysql://localhost:3306/tpch_star_small");
        tpch_star_small.setUsername(user);
        tpch_star_small.setPassword(pass);
        hashMap.put("tpch_star_small", tpch_star_small);

        DriverManagerDataSource tpch_star_sf1 = new DriverManagerDataSource();
        tpch_star_sf1.setUrl("jdbc:mysql://localhost:3306/tpch_star_1gb");
        tpch_star_sf1.setUsername(user);
        tpch_star_sf1.setPassword(pass);
        hashMap.put("tpch_star_sf1", tpch_star_sf1);

        DriverManagerDataSource tpch_star_sf10 = new DriverManagerDataSource();
        tpch_star_sf10.setUrl("jdbc:mysql://localhost:3306/tpch_star_10gb");
        tpch_star_sf10.setUsername(user);
        tpch_star_sf10.setPassword(pass);
        hashMap.put("tpch_star_sf10", tpch_star_sf10);

        //SNOWFLAKE SCHEMAS

        DriverManagerDataSource tpch_snow_mini = new DriverManagerDataSource();
        tpch_snow_mini.setUrl("jdbc:mysql://localhost:3306/tpch_snow_mini");
        tpch_snow_mini.setUsername(user);
        tpch_snow_mini.setPassword(pass);
        hashMap.put("tpch_snow_mini", tpch_snow_mini);

        DriverManagerDataSource tpch_snow_small = new DriverManagerDataSource();
        tpch_snow_small.setUrl("jdbc:mysql://localhost:3306/tpch_snow_small");
        tpch_snow_small.setUsername(user);
        tpch_snow_small.setPassword(pass);
        hashMap.put("tpch_snow_small", tpch_snow_small);

        DriverManagerDataSource tpch_snow_sf1 = new DriverManagerDataSource();
        tpch_snow_sf1.setUrl("jdbc:mysql://localhost:3306/tpch_snow_1gb");
        tpch_snow_sf1.setUsername(user);
        tpch_snow_sf1.setPassword(pass);
        hashMap.put("tpch_snow_sf1", tpch_snow_sf1);

        DriverManagerDataSource tpch_snow_sf10 = new DriverManagerDataSource();
        tpch_snow_sf10.setUrl("jdbc:mysql://localhost:3306/tpch_snow_10gb");
        tpch_snow_sf10.setUsername(user);
        tpch_snow_sf10.setPassword(pass);
        hashMap.put("tpch_snow_sf10", tpch_snow_sf10);

        //STATS DB

        DriverManagerDataSource stats = new DriverManagerDataSource();
        stats.setUrl("jdbc:mysql://localhost:3306/stats");
        stats.setUsername(user);
        stats.setPassword(pass);
        hashMap.put("stats", stats);

        return hashMap;
    }
}