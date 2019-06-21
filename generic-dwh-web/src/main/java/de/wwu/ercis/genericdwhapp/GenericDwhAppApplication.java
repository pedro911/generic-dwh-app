package de.wwu.ercis.genericdwhapp;

import de.wwu.ercis.genericdwhapp.services.AllDBMasterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GenericDwhAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericDwhAppApplication.class, args);
    }

    @Bean
    public DataSource allDBsDataSource(){
        CustomRoutingDataSource customDataSource=new CustomRoutingDataSource();
        customDataSource.setTargetDataSources(AllDBMasterService.getDataSourceHashMap());
        return customDataSource;
    }

}
