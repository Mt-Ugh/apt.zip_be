package com.aptzip.user.repository;

import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져옴
}
