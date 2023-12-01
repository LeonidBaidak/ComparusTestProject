package com.baidak.test_comparus.repository;

import com.baidak.test_comparus.domain.User;
import com.baidak.test_comparus.domain.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationFactory {

    public Specification<User> conjunction() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();
    }

    public Specification<User> likeUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(User_.USERNAME), username);
    }

    public Specification<User> likeFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(User_.FIRST_NAME), firstName);
    }

    public Specification<User> likeSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(User_.SURNAME), surname);
    }
}
