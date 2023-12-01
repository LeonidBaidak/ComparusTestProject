package com.baidak.test_comparus.service;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.filter.UserFilter;

import java.util.List;

public interface UserService {

    List<User> findAll(UserFilter userFilter);
}
