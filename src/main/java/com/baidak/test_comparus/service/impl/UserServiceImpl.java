package com.baidak.test_comparus.service.impl;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.dto.filter.UserFilter;
import com.baidak.test_comparus.repository.UserRepository;
import com.baidak.test_comparus.repository.UserSpecificationFactory;
import com.baidak.test_comparus.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String PATTERN_CHAR_FROM_API = "\\*";
    private static final String PATTERN_CHAR_TO_DB = "%";

    private final UserRepository userRepository;
    private final UserSpecificationFactory userSpecificationFactory;

    @Override
    public List<User> findAll(UserFilter userFilter) {
        Specification<User> specification = buildUserSpecification(userFilter);
        return userRepository.findAll(specification);
    }

    private Specification<User> buildUserSpecification(UserFilter userFilter) {
        Specification<User> specification = userSpecificationFactory.conjunction();
        if (userFilter.getUsername() != null) {
            specification = specification.and(userSpecificationFactory
                    .likeUsername(userFilter
                            .getUsername()
                            .replaceAll(PATTERN_CHAR_FROM_API, PATTERN_CHAR_TO_DB)));
        }
        if (userFilter.getFirstName() != null) {
            specification = specification.and(userSpecificationFactory
                    .likeFirstName(userFilter
                            .getFirstName()
                            .replaceAll(PATTERN_CHAR_FROM_API, PATTERN_CHAR_TO_DB)));
        }
        if (userFilter.getSurname() != null) {
            specification = specification.and(userSpecificationFactory
                    .likeSurname(userFilter
                            .getSurname()
                            .replaceAll(PATTERN_CHAR_FROM_API, PATTERN_CHAR_TO_DB)));
        }
        return specification;
    }
}
