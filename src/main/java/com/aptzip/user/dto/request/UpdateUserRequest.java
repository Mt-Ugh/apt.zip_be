package com.aptzip.user.dto.request;

import com.aptzip.user.entity.User;

public record UpdateUserRequest(
        String nickname,
        String email,
        String password) {
}
