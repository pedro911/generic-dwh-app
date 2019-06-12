package de.wwu.ercis.genericdwhapp.model.configuration;

import com.zaxxer.hikari.HikariDataSource;
import de.wwu.ercis.genericdwhapp.model.standard.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ConditionalOnExpression("#{environment.acceptsProfiles('alldb')}")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages = "de.wwu.ercis.genericdwhapp.repositories.standard.small",
        entityManagerFactoryRef = "tpchsmallEntityManagerFactory",
        transactionManagerRef= "tpchsmallTransactionManager"
)
public class TPCHStandardSmallDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.tpchstd.small")
    public DataSourceProperties tpchsmallDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.tpchstd.small.configuration")
    public DataSource tpchsmallDataSource() {
        return tpchsmallDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "tpchsmallEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tpchsmallEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("Building tpchsmall app.datasource.tpchstd.small");
        return builder.dataSource(tpchsmallDataSource())
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

    @Bean
    public PlatformTransactionManager tpchsmallTransactionManager(
            final @Qualifier("tpchsmallEntityManagerFactory")
                    LocalContainerEntityManagerFactoryBean tpchsmallEntityManagerFactory) {
        return new JpaTransactionManager(tpchsmallEntityManagerFactory.getObject());
    }

}
