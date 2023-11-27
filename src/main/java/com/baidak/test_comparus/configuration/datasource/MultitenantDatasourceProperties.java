package com.baidak.test_comparus.configuration.datasource;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "com.baidak.test-comparus")
public class MultitenantDatasourceProperties {

    @Size(min = 1, message = "At least one data source must be initialized via properties!")
    private final List<CustomDataSourceProperties> dataSources;

    @Data
    public static class CustomDataSourceProperties {

        private final String name;
        private final String url;
        private final String table;
        private final String user;
        private final String password;
        private final Mapping mapping;
    }

    @Data
    public static class Mapping {

        private final String id;
        private final String username;
        private final String name;
        private final String surname;
    }
}
