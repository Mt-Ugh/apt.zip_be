package com.aptzip.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
