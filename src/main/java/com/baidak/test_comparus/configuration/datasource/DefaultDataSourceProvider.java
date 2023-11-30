package com.baidak.test_comparus.configuration.datasource;

import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DatabaseDriver.POSTGRESQL;


@Component
@RequiredArgsConstructor
public class DefaultDataSourceProvider implements DataSourceProvider {

    @Override
    public DataSource buildDataSource(DataSourceDefinition dataSourceDefinition) {
        return DataSourceBuilder.create()
                .url(dataSourceDefinition.getUrl())
                .username(dataSourceDefinition.getUser())
                .password(dataSourceDefinition.getPassword())
                //NOTE: The driver class may be implemented via properties and provided from outside
                .driverClassName(POSTGRESQL.getDriverClassName())
                .build();
    }
}
