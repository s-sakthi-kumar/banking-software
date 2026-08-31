package com.example.rdsdemo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Builds a HikariCP DataSource using a pre-generated IAM auth token
 * passed in via the RDS_TOKEN environment variable.
 *
 * Generate the token with:
 *   export RDS_TOKEN=$(aws rds generate-db-auth-token \
 *     --hostname database-sk.cluster-cxmamqkyqvnd.ap-south-1.rds.amazonaws.com \
 *     --port 5432 --username postgres --region ap-south-1)
 */
@Configuration
public class RdsDataSourceConfig {

    @Value("${rds.host}")
    private String host;

    @Value("${rds.port}")
    private int port;

    @Value("${rds.dbname}")
    private String dbName;

    @Value("${rds.username}")
    private String username;

    @Value("${RDS_TOKEN}")
    private String rdsToken;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format(
                "jdbc:postgresql://%s:%d/%s?sslmode=require", host, port, dbName));
        config.setUsername(username);
        config.setPassword(rdsToken);  // IAM token used directly as password

        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30_000);
        config.setIdleTimeout(600_000);
        // Keep below IAM token TTL of 15 min (900000ms)
        config.setMaxLifetime(840_000);

        return new HikariDataSource(config);
    }
}
