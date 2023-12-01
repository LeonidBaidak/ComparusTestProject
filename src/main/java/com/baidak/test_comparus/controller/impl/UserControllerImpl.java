package com.baidak.test_comparus.controller.impl;

import com.baidak.test_comparus.controller.UserController;
import com.baidak.test_comparus.dto.filter.UserFilter;
import com.baidak.test_comparus.dto.response.UserReadResponse;
import com.baidak.test_comparus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final ConversionService conversionService;

    @Override
    public List<UserReadResponse> findAll(UserFilter userFilter) {
        return userService.findAll(userFilter)
                .stream()
                .map(user -> conversionService.convert(user, UserReadResponse.class))
                .toList();
    }
}
