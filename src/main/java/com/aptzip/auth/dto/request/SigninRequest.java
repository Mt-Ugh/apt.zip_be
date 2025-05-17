package com.aptzip.auth.dto.request;

public record SigninRequest(
        String email,
        String password) {
}
