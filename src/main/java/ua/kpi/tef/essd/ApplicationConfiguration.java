package ua.kpi.tef.essd;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "ua.kpi.tef.essd.service", "ua.kpi.tef.essd.dao"})
public class ApplicationConfiguration {

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("ua.kpi.tef.essd.jpa");
        return localEmfBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    /*@Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(this.entityManagerFactory);
        txManager.setDataSource(this.dataSource);
        return txManager;
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory() { //
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();

        emfb.setDataSource(this.dataSource);
        emfb.setJpaVendorAdapter(createJpaVendorAdapter());
        emfb.setJpaProperties(createHibernateProperties());
        emfb.afterPropertiesSet();

        //emfb.setPersistenceUnitName("persistence.xml");
        return emfb;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("clothes_designer_app");
        dataSource.setPassword("CDUa*D?aGpHs87/");
        dataSource.setUrl("jdbc:mysql://localhost:3306/clothes_designer");

        return dataSource;
    }

    private JpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties createHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.hbm2ddl.import_files",
                "/database_dump/properties.sql," +
                "/database_dump/parts.sql," +
                "/database_dump/part_property.sql," +
                "/database_dump/users.sql," +
                "/database_dump/clothes_sets.sql," +
                "/database_dump/clothes.sql," +
                "/database_dump/clothing_part.sql");
        return properties;
    }*/
}
