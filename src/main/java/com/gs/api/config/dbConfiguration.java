
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.config;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.gs.api.db.repositories", entityManagerFactoryRef = "dbEntityManager", transactionManagerRef = "dbTransactionManager")
public class dbConfiguration {

	@Autowired
	private Environment env;

	public dbConfiguration() {
		super();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean dbEntityManager() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dbDataSource());
		em.setPackagesToScan("com.gs.api.db.entity");

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		final HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("datasource.app.hibernate.hbm2ddl.method"));
		properties.put("hibernate.dialect", env.getProperty("datasource.app.hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean

	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dbDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager dbTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(dbEntityManager().getObject());
		return transactionManager;
	}

}
