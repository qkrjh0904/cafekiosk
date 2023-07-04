package sample.cafekiosk.spring.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Profile("test")
@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"sample.cafekiosk.spring.domain"})
@EnableJpaRepositories(basePackages = {"sample.cafekiosk.spring"})
@RequiredArgsConstructor
public class TestEntityManagerConfig {

    private static final String[] PACKAGE_TO_SCAN = {"sample.cafekiosk.spring"};
    private final DataSource dataSource;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.HBM2DDL_AUTO, "create");
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put(AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, 500);
        properties.put(AvailableSettings.DIALECT, MariaDBDialect.class);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(PACKAGE_TO_SCAN);
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.setJpaPropertyMap(properties);
        return emf;
    }

}
