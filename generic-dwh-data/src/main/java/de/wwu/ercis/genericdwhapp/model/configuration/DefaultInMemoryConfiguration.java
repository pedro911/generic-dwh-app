package de.wwu.ercis.genericdwhapp.model.configuration;

import com.zaxxer.hikari.HikariDataSource;
import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ConditionalOnExpression("#{environment.acceptsProfiles('default')}")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages = "de.wwu.ercis.genericdwhapp.repositories.genericdwh",
        entityManagerFactoryRef = "defaultEntityManagerFactory",
        transactionManagerRef= "defaultTransactionManager"
)
public class DefaultInMemoryConfiguration {

    @Bean
    @Primary
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource defaultDataSource() {
        return defaultDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "defaultEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("Building default data source genericdwh from slides");
        return builder.dataSource(defaultDataSource())
                .packages(
                        Dimension.class,
                        DimensionCombination.class,
                        DimensionHierarchy.class,
                        Fact.class,
                        Ratio.class,
                        RatioCombination.class,
                        ReferenceObject.class,
                        ReferenceObjectCombination.class,
                        ReferenceObjectHierarchy.class
                )
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager defaultTransactionManager(
            final @Qualifier("defaultEntityManagerFactory")
                    LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory) {
        return new JpaTransactionManager(defaultEntityManagerFactory.getObject());
    }
}
