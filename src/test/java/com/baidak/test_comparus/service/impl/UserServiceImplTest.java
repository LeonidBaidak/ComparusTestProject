package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.repository.impl.UserRepositoryImpl;
import com.baidak.test_comparus.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserRepositoryImpl userRepositoryImpl = mock(UserRepositoryImpl.class);
    private final UserService service = new UserServiceImpl(userRepositoryImpl);

    @Test
    void testFindAll() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<User> expectedResult = getUserTestData(id1, id2);
        when(userRepositoryImpl.findAll()).thenReturn(getUserTestData(id1, id2));
        List<User> actualResult = service.findAll();
        assertEquals(expectedResult, actualResult);
        verify(userRepositoryImpl, times(1)).findAll();
        verifyNoMoreInteractions(userRepositoryImpl);
    }

    private List<User> getUserTestData(UUID id1, UUID id2) {
        User user1 = User.builder()
                .id(id1)
                .firstName("name1")
                .surname("surname1")
                .username("username1")
                .build();
        User user2 = User.builder()
                .id(id2)
                .firstName("name2")
                .surname("surname2")
                .username("username2")
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        return users;
    }
}