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
@EnableJpaRepositories(basePackages = "de.wwu.ercis.genericdwhapp.repositories.standard.onegb",
        entityManagerFactoryRef = "tpchonegbEntityManagerFactory",
        transactionManagerRef= "tpchonegbTransactionManager"
)
public class TPCHStandardOneGBDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.tpchstd.onegb")
    public DataSourceProperties tpchonegbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.tpchstd.onegb.configuration")
    public DataSource tpchonegbDataSource() {
        return tpchonegbDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "tpchonegbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tpchonegbEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("Building tpchstd app.datasource.tpchstd.onegb");
        return builder.dataSource(tpchonegbDataSource())
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
    public PlatformTransactionManager tpchonegbTransactionManager(
            final @Qualifier("tpchonegbEntityManagerFactory")
                    LocalContainerEntityManagerFactoryBean tpchonegbEntityManagerFactory) {
        return new JpaTransactionManager(tpchonegbEntityManagerFactory.getObject());
    }

}
