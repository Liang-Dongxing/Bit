package com.bit.framework.config;

import com.bit.common.enums.DataSourceType;
import com.bit.common.utils.spring.SpringUtils;
import com.bit.framework.config.properties.DataSourceProperties;
import com.bit.framework.datasource.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid 配置多数据源
 *
 * @author bit
 */
@Configuration
@ConditionalOnProperty(value = "spring.datasource.master.enabled",havingValue = "true")
public class DataSourceConfig {
    @Value("${spring.datasource.master.url}")
    private String masterUrl;
    @Value("${spring.datasource.master.username}")
    private String masterUsername;
    @Value("${spring.datasource.master.password}")
    private String masterPassword;
    @Value("${spring.datasource.master.pool-name}")
    private String masterPoolName;
    @Value("${spring.datasource.slave.url}")
    private String slaveUrl;
    @Value("${spring.datasource.slave.username}")
    private String slaveUsername;
    @Value("${spring.datasource.slave.password}")
    private String slavePassword;
    @Value("${spring.datasource.slave.pool-name}")
    private String slavePoolName;

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(masterUrl);
        hikariDataSource.setUsername(masterUsername);
        hikariDataSource.setPassword(masterPassword);
        hikariDataSource.setPoolName(masterPoolName);
        return dataSourceProperties.dataSource(hikariDataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(slaveUrl);
        hikariDataSource.setUsername(slaveUsername);
        hikariDataSource.setPassword(slavePassword);
        hikariDataSource.setPoolName(slavePoolName);
        return dataSourceProperties.dataSource(hikariDataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 设置数据源
     *
     * @param targetDataSources 备选数据源集合
     * @param sourceName        数据源名称
     * @param beanName          bean名称
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = SpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {
        }
    }

}
