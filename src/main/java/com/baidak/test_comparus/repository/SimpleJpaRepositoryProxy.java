package com.baidak.test_comparus.repository;

import com.baidak.test_comparus.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class SimpleJpaRepositoryProxy {

    private final UserRepository userRepository;

    public List<User> findAll() {
        log.warn("Before repository method call");
        List<User> result = userRepository.findAll();
        log.warn("After repository method call:\n" + result);
        return result;
    }
}
