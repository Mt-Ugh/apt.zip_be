package com.aptzip.user.service;

import com.aptzip.user.dto.request.AddUserRequest;
import com.aptzip.user.dto.request.UpdateProfileUrlRequest;
import com.aptzip.user.dto.request.UpdateUserRequest;
import com.aptzip.user.dto.response.UserDetailResponse;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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

    public UserDetailResponse findByUuid(String userUuid) {
        User user = userRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        return new UserDetailResponse(
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    @Transactional
    public void updateUser(String userUuid, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        String encodedPassword = null;
        if (updateUserRequest.password() != null && !updateUserRequest.password().isBlank()) {
            encodedPassword = passwordEncoder.encode(updateUserRequest.password());
        }

        user.update(
                updateUserRequest.nickname(),
                updateUserRequest.email(),
                encodedPassword
        );
    }

    @Transactional
    public void updateProfileUrl(String userUuid, UpdateProfileUrlRequest updateProfileUrlRequest) {
        User user = userRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        user.updateProfileUrl(updateProfileUrlRequest.profileUrl());
    }
}
