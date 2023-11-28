package com.baidak.test_comparus.configuration.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties(MultiTenantDatasourceProperties.class)
public class DataSourceConfiguration {
}
