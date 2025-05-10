package com.aptzip.user.service;

import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public String save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = dto.toEntity();
        user.encodePassword(encoder);

        return userRepository.save(user).getUserUuid();
    }

    public User findById(String userUuid) {
        return userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
