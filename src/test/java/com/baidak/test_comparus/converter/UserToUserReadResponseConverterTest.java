package com.baidak.test_comparus.converter;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.response.UserReadResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserToUserReadResponseConverterTest {

    UserToUserReadResponseConverter converter = new UserToUserReadResponseConverter();

    @Test
    void convert() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("name1")
                .surname("surname1")
                .username("username1")
                .build();

        UserReadResponse expectedResult = UserReadResponse.builder()
                .id(id)
                .name("name1")
                .surname("surname1")
                .username("username1")
                .build();

        UserReadResponse actualResult = converter.convert(user);

        assertEquals(expectedResult, actualResult);
    }
}