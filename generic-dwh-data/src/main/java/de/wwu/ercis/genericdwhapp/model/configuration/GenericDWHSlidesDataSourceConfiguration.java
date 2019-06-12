package de.wwu.ercis.genericdwhapp.model.configuration;

import com.zaxxer.hikari.HikariDataSource;
import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConditionalOnExpression("#{environment.acceptsProfiles('alldb')}")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages = "de.wwu.ercis.genericdwhapp.repositories.genericdwh",
        entityManagerFactoryRef = "genericdwhEntityManagerFactory",
        transactionManagerRef= "genericdwhTransactionManager"
)
public class GenericDWHSlidesDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.genericdwh.slides")
    public DataSourceProperties genericdwhDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.genericdwh.slides.configuration")
    public DataSource genericdwhDataSource() {
        return genericdwhDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "genericdwhEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean genericdwhEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("Building datasource genericdwh");
        return builder.dataSource(genericdwhDataSource())
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
    public PlatformTransactionManager genericdwhTransactionManager(
            final @Qualifier("genericdwhEntityManagerFactory")
                    LocalContainerEntityManagerFactoryBean genericdwhEntityManagerFactory) {
        return new JpaTransactionManager(genericdwhEntityManagerFactory.getObject());
    }
}
