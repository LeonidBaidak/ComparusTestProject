package com.baidak.test_comparus.configuration.datasource;

import com.baidak.test_comparus.configuration.datasource.routing_data_source.RoutingDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;

@Data
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(MultiTenantDatasourceProperties.class)
public class DataSourceConfiguration {

    DataSourceProvider dataSourceProvider;

    @Bean
    DataSource routingDataSource(MultiTenantDatasourceProperties multiTenantDatasourceProperties,
                                 DataSourceProvider dataSourceProvider,
                                 TargetDataSourceContextHolder dataSourceContextHolder) {
        return new RoutingDataSource(multiTenantDatasourceProperties, dataSourceProvider, dataSourceContextHolder);
    }
}
