package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.response.UserReadResponse;
import com.baidak.test_comparus.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private final UserService service = new UserServiceImpl();

    @Test
    void testFindAll(){
        List<User> result = service.findAll();
        assertEquals(3, result.size());
    }

    private List<User> getUserTestData(){
        User user1 = User.builder()
                .id(UUID.randomUUID())
                .name("name1")
                .surname("surname1")
                .username("username1")
                .build();
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .name("name2")
                .surname("surname2")
                .username("username2")
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        return users;
    }

    private List<UserReadResponse> getUserReadResponseTestData(){
        UserReadResponse user1 = UserReadResponse.builder()
                .id(UUID.randomUUID())
                .name("name1")
                .surname("surname1")
                .username("username1")
                .build();
        UserReadResponse user2 = UserReadResponse.builder()
                .id(UUID.randomUUID())
                .name("name2")
                .surname("surname2")
                .username("username2")
                .build();

        List<UserReadResponse> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        return users;
    }
}