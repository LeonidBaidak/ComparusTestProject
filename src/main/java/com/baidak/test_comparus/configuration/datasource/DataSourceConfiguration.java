package com.baidak.test_comparus.configuration.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
@EnableConfigurationProperties(MultiTenantDatasourceProperties.class)
public class DataSourceConfiguration {

    DataSourceProvider dataSourceProvider;

    @Bean
    DataSource dataSource(MultiTenantDatasourceProperties multiTenantDatasourceProperties,
                          DataSourceProvider dataSourceProvider){
        return dataSourceProvider.buildDataSource(multiTenantDatasourceProperties.getDataSourceDefinitions().get(0));
    }
}
