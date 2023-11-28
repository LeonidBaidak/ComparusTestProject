package com.baidak.test_comparus.repository.impl;

import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties;
import com.baidak.test_comparus.configuration.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;
import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_SELECT = "SELECT id, username, first_name, surname FROM user_table";

    private final MultiTenantDatasourceProperties multiTenantDatasourceProperties;

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        for (DataSourceDefinition dataSourceDefinition : multiTenantDatasourceProperties.getDataSourceDefinitions()) {
            List<User> selectedData = selectUsers(dataSourceDefinition);
            result.addAll(selectedData);
        }
        return result;
    }

    private List<User> selectUsers(DataSourceDefinition dataSourceDefinition) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dataSourceDefinition.getUrl(),
                dataSourceDefinition.getUser(), dataSourceDefinition.getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT)) {
            log.debug("The selected records are:");
            int rowCount = 0;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String surname = resultSet.getString("surname");
                log.debug(id + ", " + username + ", " + firstName + ", " + surname);
                rowCount++;
                User user = User.builder()
                        //TODO Need to add IllegalArgumentException case to ControllerAdvice
                        .id(UUID.fromString(id))
                        .username(username)
                        .firstName(firstName)
                        .surname(surname)
                        .build();
                users.add(user);
            }
            log.warn(rowCount + " rows were read.");
        } catch (SQLException exception) {
            //TODO Add custom runtime exception, to break the flow and return 500x response to user
            log.warn("Database exception occurred!", exception);
        }
        return users;
    }
}
