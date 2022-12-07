package com.bit.framework.config.properties;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * druid 配置属性
 *
 * @author bit
 */
@Configuration
public class DataSourceProperties {
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeout;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idleTimeout;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int maxLifetime;


    public HikariDataSource dataSource(HikariDataSource datasource) {
        datasource.setConnectionTimeout(connectionTimeout);
        datasource.setMaximumPoolSize(maximumPoolSize);
        datasource.setMinimumIdle(minimumIdle);
        datasource.setIdleTimeout(idleTimeout);
        datasource.setMaxLifetime(maxLifetime);
        return datasource;
    }
}
