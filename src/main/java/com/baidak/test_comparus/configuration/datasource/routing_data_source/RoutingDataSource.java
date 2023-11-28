package com.baidak.test_comparus.configuration.datasource.routing_data_source;

import com.baidak.test_comparus.configuration.datasource.DataSourceProvider;
import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties;
import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;
import com.baidak.test_comparus.configuration.datasource.TargetDataSourceContextHolder;
import com.baidak.test_comparus.configuration.datasource.TargetDataSourceContextHolder.DataSourceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class RoutingDataSource extends AbstractDataSource {

    private final MultiTenantDatasourceProperties multiTenantDatasourceProperties;
    private final DataSourceProvider dataSourceProvider;
    private final TargetDataSourceContextHolder targetDataSourceContextHolder;
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final String primaryDataSourceName;

    public RoutingDataSource(MultiTenantDatasourceProperties multiTenantDatasourceProperties,
                             DataSourceProvider dataSourceProvider,
                             TargetDataSourceContextHolder targetDataSourceContextHolder) {
        this.multiTenantDatasourceProperties = multiTenantDatasourceProperties;
        this.dataSourceProvider = dataSourceProvider;
        this.targetDataSourceContextHolder = targetDataSourceContextHolder;
        DataSourceDefinition primaryDataSourceDefinition = multiTenantDatasourceProperties
                .getDataSourceDefinitions()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No datasource definition available!"));
        DataSource primaryDataSource = dataSourceProvider.buildDataSource(primaryDataSourceDefinition);
        this.primaryDataSourceName = primaryDataSourceDefinition.getName();
        dataSources.put(primaryDataSourceName, primaryDataSource);
    }


    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    private DataSource determineTargetDataSource() {
        DataSource targetDataSource;
        DataSourceContext dataSourceContext = targetDataSourceContextHolder.getDataSourceContext();
        /*
            NOTE: The problem with the code here is that Hibernate demands Connection on an Application startup .
            But we do not have dataSourceContext on startup. The better way would be to modify this behavior,
            possibly via properties, and replace body of "if" with custom exception.
        */
        if (Objects.isNull(dataSourceContext)) {
            log.warn("DataSource context has not been initialized!!!");
            return dataSources.get(primaryDataSourceName);
        }
        String dataSourceName = dataSourceContext.getName();
        if (dataSources.containsKey(dataSourceContext.getName())) {
            targetDataSource = dataSources.get(dataSourceName);
        } else {
            DataSourceDefinition dataSourceDefinition = multiTenantDatasourceProperties.getDataSourceDefinitions()
                    .stream()
                    .filter(s -> s.getName().equals(dataSourceName)).findFirst()
                    //TODO Use custom exception!!!
                    .orElseThrow(() -> new RuntimeException("TargetDataSource with specified name does not present in DataSourceDefinitions"));
            targetDataSource = dataSources.computeIfAbsent(dataSourceName, s -> {
                log.trace("Build new DataSource");
                return dataSourceProvider.buildDataSource(dataSourceDefinition);
            });
        }
        return targetDataSource;
    }
}
