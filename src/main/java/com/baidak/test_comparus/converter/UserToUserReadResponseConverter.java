package com.baidak.test_comparus.converter;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.response.UserReadResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserReadResponseConverter implements Converter<User, UserReadResponse> {

    @Override
    public UserReadResponse convert(User source) {
        return UserReadResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .username(source.getUsername())
                .build();
    }
}
