package com.baidak.test_comparus.configuration.datasource;

import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;

import javax.sql.DataSource;

public interface DataSourceProvider {

    DataSource buildDataSource(DataSourceDefinition dataSourceDefinition);
}
