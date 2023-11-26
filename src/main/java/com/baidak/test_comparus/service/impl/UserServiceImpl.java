package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.repository.UserRepository;
import com.baidak.test_comparus.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
