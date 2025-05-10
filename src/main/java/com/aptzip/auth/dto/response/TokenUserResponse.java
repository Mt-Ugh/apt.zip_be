package com.aptzip.auth.dto.response;

public record TokenUserResponse(
        String accessToken,
        String refreshToken,
        String userUuid,
        String nickname,
        String profileUrl) {
}
