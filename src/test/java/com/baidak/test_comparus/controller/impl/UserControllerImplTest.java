package com.baidak.test_comparus.controller.impl;

import com.baidak.test_comparus.controller.DefaultControllerAdvice;
import com.baidak.test_comparus.controller.UserController;
import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.filter.UserFilter;
import com.baidak.test_comparus.dto.response.UserReadResponse;
import com.baidak.test_comparus.exception.MultithreadingTaskFailedException;
import com.baidak.test_comparus.exception.TargetDataSourceDoesNotDefinedException;
import com.baidak.test_comparus.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerImplTest {

    private static final String USERS_URL = "/api/v1/users";
    private static final UserFilter filter = new UserFilter();

    private final ConversionService conversionService = mock(ConversionService.class);
    private final UserService userService = mock(UserService.class);
    private final UserController controller = new UserControllerImpl(userService, conversionService);

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new DefaultControllerAdvice())
                .build();
    }

    @Test
    void testFindAll() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
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

        UserReadResponse userReadResponse1 = UserReadResponse.builder()
                .id(id1)
                .firstName("name1")
                .surname("surname1")
                .username("username1")
                .build();
        UserReadResponse userReadResponse2 = UserReadResponse.builder()
                .id(id2)
                .firstName("name2")
                .surname("surname2")
                .username("username2")
                .build();

        when(userService.findAll(filter)).thenReturn(users);
        when(conversionService.convert(user1, UserReadResponse.class)).thenReturn(userReadResponse1);
        when(conversionService.convert(user2, UserReadResponse.class)).thenReturn(userReadResponse2);

        mockMvc.perform(get(USERS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id")
                        .value(user1.getId().toString()))
                .andExpect(jsonPath("$[0].username")
                        .value(user1.getUsername()))
                .andExpect(jsonPath("$[0].firstName")
                        .value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].surname")
                        .value(user1.getSurname()))
                .andExpect(jsonPath("$[1].id")
                        .value(user2.getId().toString()))
                .andExpect(jsonPath("$[1].username")
                        .value(user2.getUsername()))
                .andExpect(jsonPath("$[1].firstName")
                        .value(user2.getFirstName()))
                .andExpect(jsonPath("$[1].surname")
                        .value(user2.getSurname()));

        verify(conversionService, times(1)).convert(user1, UserReadResponse.class);
        verify(conversionService, times(1)).convert(user2, UserReadResponse.class);
        verify(userService, times(1)).findAll(filter);
        verifyNoMoreInteractions(userService, conversionService);
    }

    @Test
    void testFindAllMethodNotAllowedException() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        patch(USERS_URL))
                .andExpect(status().is(405))
                .andReturn();

        assertNotNull(result.getResolvedException());
        assertEquals(HttpRequestMethodNotSupportedException.class, result.getResolvedException().getClass());
        verifyNoMoreInteractions(userService, conversionService);
    }

    @Test
    void testFindAllHttpMediaTypeNotAcceptable() throws Exception {
        MvcResult result = mockMvc
                .perform(get(USERS_URL)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertNotNull(result.getResolvedException());
        assertEquals(HttpMediaTypeNotAcceptableException.class, result.getResolvedException().getClass());
        verifyNoMoreInteractions(userService, conversionService);
    }

    @Test
    void testFindAllMultithreadingTaskFailedException() throws Exception {
        when(userService.findAll(filter)).thenThrow(new MultithreadingTaskFailedException("msg",
                new RuntimeException()));
        MvcResult result = mockMvc
                .perform(get(USERS_URL))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertNotNull(result.getResolvedException());
        assertEquals(MultithreadingTaskFailedException.class, result.getResolvedException().getClass());
        verify(userService, times(1)).findAll(filter);
        verifyNoMoreInteractions(userService, conversionService);
    }

    @Test
    void testFindAllTargetDataSourceDoesNotDefinedException() throws Exception {
        when(userService.findAll(filter)).thenThrow(new TargetDataSourceDoesNotDefinedException("msg"));
        MvcResult result = mockMvc
                .perform(get(USERS_URL))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertNotNull(result.getResolvedException());
        assertEquals(TargetDataSourceDoesNotDefinedException.class, result.getResolvedException().getClass());
        verify(userService, times(1)).findAll(filter);
        verifyNoMoreInteractions(userService, conversionService);
    }

    @Test
    void testFindAllGeneralThrowable() throws Exception {
        when(userService.findAll(filter)).thenThrow(new RuntimeException("msg"));
        MvcResult result = mockMvc
                .perform(get(USERS_URL))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertNotNull(result.getResolvedException());
        assertEquals(RuntimeException.class, result.getResolvedException().getClass());
        verify(userService, times(1)).findAll(filter);
        verifyNoMoreInteractions(userService, conversionService);
    }
}