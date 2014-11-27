package com.oshurpik.config;

import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile("dev")
public class TestEntityManagerContext {
    
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "test.hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "test.hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO = "test.hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA = "test.hibernate.default_schema";
    
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "test.db.driver";
    private static final String PROPERTY_NAME_DATABASE_URL = "test.db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "test.db.username";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "test.db.password";
    
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    @Resource
    private Environment env;


    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));        
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DLL_AUTO));
        properties.put(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA));
        return properties;	
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(dataSource());
       em.setPackagesToScan(new String[] { env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN) });

       HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       vendorAdapter.setGenerateDdl(true);
       em.setJpaVendorAdapter(vendorAdapter);
       em.setJpaProperties(hibProperties());

       return em;
    }
    
    @Bean
    public DataSource dataSource() {
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }
    
}
