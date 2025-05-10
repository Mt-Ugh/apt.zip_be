package com.aptzip.auth.dto.response;

public record LoginResponse(
        String accessToken,
        String userUuid,
        String nickname,
        String profileUrl) {
}
