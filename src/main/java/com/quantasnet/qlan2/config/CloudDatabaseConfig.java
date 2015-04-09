package com.quantasnet.qlan2.config;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Profile("cloud")
@Configuration
public class CloudDatabaseConfig {

    private static final String DRIVER_CLASS = Driver.class.getName();

    @Value("#{environment['DATABASE_URL']}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() throws URISyntaxException {

        final URI dbUri = new URI(databaseUrl);
        final String url = dbUri.getHost();
        final int port = dbUri.getPort();
        final String username = dbUri.getUserInfo().split(":")[0];
        final String password = dbUri.getUserInfo().split(":")[1];

        final HikariDataSource hkds = new HikariDataSource();
        hkds.setJdbcUrl("jdbc:postgresql://" + url + ":" + port + dbUri.getPath());
        hkds.setUsername(username);
        hkds.setPassword(password);
        hkds.setDriverClassName(DRIVER_CLASS);
        return hkds;
    }
}
