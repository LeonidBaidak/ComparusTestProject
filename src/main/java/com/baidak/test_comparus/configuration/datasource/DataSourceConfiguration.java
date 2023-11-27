package com.baidak.test_comparus.configuration.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties(MultitenantDatasourceProperties.class)
public class DataSourceConfiguration {
//
//    private final MultitenantDatasourceProperties multitenantDatasourceProperties;
//
//    @Bean
//    @Primary
//    DataSource dataSource(MultitenantDatasourceProperties multitenantDatasourceProperties)  {
//        MultitenantDatasourceProperties.CustomDataSourceProperties customDataSourceProperties = multitenantDatasourceProperties.getDataSources().get(0);
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(customDataSourceProperties.getUrl());
//        hikariConfig.setUsername(customDataSourceProperties.getName());
//        hikariConfig.setPassword(customDataSourceProperties.getPassword());
//        hikariConfig.setDriverClassName("org.postgresql.Driver");
//        hikariConfig.setMaximumPoolSize(10);
//        hikariConfig.setPoolName("DataSource" + hikariConfig.hashCode());
//        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
//        return hikariDataSource;
//    }
//
//    @Bean
//    @Primary
//    DataSourceTransactionManager catalogTransactionManager(
//            @Qualifier("dataSource") DataSource datasource) {
//        return new DataSourceTransactionManager(datasource);
//    }
}
