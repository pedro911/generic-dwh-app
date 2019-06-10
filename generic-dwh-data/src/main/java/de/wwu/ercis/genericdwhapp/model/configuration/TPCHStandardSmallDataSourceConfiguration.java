package de.wwu.ercis.genericdwhapp.model.configuration;

import com.zaxxer.hikari.HikariDataSource;
import de.wwu.ercis.genericdwhapp.model.standard.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.wwu.ercis.genericdwhapp.repositories.standard.small",
        entityManagerFactoryRef = "standardEntityManagerFactory",
        transactionManagerRef= "standardTransactionManager"
)
public class TPCHStandardSmallDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.tpchstd.small")
    public DataSourceProperties standardDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.tpchstd.small.configuration")
    public DataSource standardDataSource() {
        return standardDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "standardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean standardEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("Building tpchstd app.datasource.tpchstd.small");
        return builder.dataSource(standardDataSource())
                .packages(
                        CustomerEntity.class,
                        LineitemEntity.class,
                        NationEntity.class,
                        OrdersEntity.class,
                        PartEntity.class,
                        PartsuppEntity.class,
                        RegionEntity.class,
                        SupplierEntity.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager standardTransactionManager(
            final @Qualifier("standardEntityManagerFactory")
                    LocalContainerEntityManagerFactoryBean standardEntityManagerFactory) {
        return new JpaTransactionManager(standardEntityManagerFactory.getObject());
    }

}
