package com.aptzip.user.dto.request;

import com.aptzip.user.entity.User;

public record AddUserRequest(
        String name,
        String nickname,
        String email,
        String password,
        String phoneNumber,
        String profileUrl) {

    public User toEntity() {
        return User.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .profileUrl(profileUrl)
                .build();
    }
}
