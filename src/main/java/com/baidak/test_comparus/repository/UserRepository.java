package com.baidak.test_comparus.repository;

import com.baidak.test_comparus.configuration.datasource.MultitenantDatasourceProperties;
import com.baidak.test_comparus.configuration.datasource.MultitenantDatasourceProperties.CustomDataSourceProperties;
import com.baidak.test_comparus.domain.User;
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
public class UserRepository {

    private static final String SQL_SELECT = "SELECT id, username, first_name, surname FROM user_table";

    private final MultitenantDatasourceProperties multitenantDatasourceProperties;

    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        for(CustomDataSourceProperties customDataSourceProperties : multitenantDatasourceProperties.getDataSources()){
            List<User> selectedData = selectUser(customDataSourceProperties);
            result.addAll(selectedData);
        }
        return result;
    }

    private List<User>  selectUser(CustomDataSourceProperties customProperties) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(customProperties.getUrl(), customProperties.getUser(),
                customProperties.getPassword());
             Statement statement = connection.createStatement();
        ) {
            log.debug("The SQL statement is: " + SQL_SELECT + "\n"); // For debugging
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            log.debug("The records selected are:\n");
            int rowCount = 0;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String surname = resultSet.getString("surname");
                log.debug(id + ", " + username + ", " + firstName + ", " + surname + "\n");
                ++rowCount;
                User user = User.builder()
                        .id(UUID.fromString(id))
                        .username(username)
                        .firstName(firstName)
                        .surname(surname)
                        .build();
                users.add(user);
            }
            log.warn("Was read " + rowCount + " rows");
        } catch (SQLException exception) {
            log.warn("Database exception occurred!", exception);
        }
        return users;
    }
}
