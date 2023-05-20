package com.example.auth_service.repository;

import com.example.auth_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserCode(String userCode);
}
