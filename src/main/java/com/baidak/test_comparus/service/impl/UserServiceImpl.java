package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public List<User> findAll() {
        return getMockUserData();
    }

    private List<User> getMockUserData(){
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
        User user3 = User.builder()
                .id(UUID.randomUUID())
                .name("name3")
                .surname("surname3")
                .username("username3")
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        return users;
    }
}
