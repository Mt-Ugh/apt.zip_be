package com.aptzip.user.dto.request;

public record AddUserRequest(
        String name,
        String nickname,
        String email,
        String password,
        String phoneNumber,
        String profileUrl) {
}
