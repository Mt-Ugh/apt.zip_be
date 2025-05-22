package com.aptzip.user.dto.response;

public record UserDetailResponse(
        String name,
        String nickname,
        String email,
        String phoneNumber,
        String profileUrl) {
}
