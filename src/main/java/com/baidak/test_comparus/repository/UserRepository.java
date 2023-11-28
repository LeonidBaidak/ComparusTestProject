package com.baidak.test_comparus.repository;

import com.baidak.test_comparus.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();
}
