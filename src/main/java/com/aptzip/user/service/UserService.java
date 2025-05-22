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

    public String save(AddUserRequest addUserRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = addUserRequest.toEntity();
        user.encodePassword(encoder);

        return userRepository.save(user).getUserUuid();
    }

    public UserDetailResponse findByUuid(String userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        return new UserDetailResponse(
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfileUrl()
        );
    }

    @Transactional
    public void updateUser(String userUuid, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userUuid)
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
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        user.updateProfileUrl(updateProfileUrlRequest.profileUrl());
    }

    @Transactional
    public void withdrawUser(String userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        userRepository.delete(user);
    }
}
