package com.baidak.test_comparus.repository;

import com.baidak.test_comparus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
