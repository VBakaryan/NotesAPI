package com.homemade.etl.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * Hikari Connection Pool configuration.
 */
@Configuration
public class DSConfig {
    private static final String HIKARICP_CLOSE_METHOD = "close";

    @Bean(name = "hikariConnection", destroyMethod = HIKARICP_CLOSE_METHOD)
    DataSource dataSource(Environment env) {
        // initializing CP
        HikariConfig dataSourceConfig = new HikariConfig();

        // Applying configuration properties
        dataSourceConfig.setDriverClassName(
                env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSourceConfig.setJdbcUrl(
                env.getRequiredProperty("spring.datasource.url"));
        dataSourceConfig.setUsername(
                env.getRequiredProperty("spring.datasource.username"));
        dataSourceConfig.setPassword(
                env.getRequiredProperty("spring.datasource.password"));
        dataSourceConfig.setMaximumPoolSize(
                Integer.valueOf(env.getRequiredProperty("spring.datasource.maximumPoolSize")));
        dataSourceConfig.setConnectionTestQuery(
                env.getRequiredProperty("spring.datasource.validation-query"));

        // returning instantiated bean
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(Environment env) {
        return new JdbcTemplate(dataSource(env));
    }

}
