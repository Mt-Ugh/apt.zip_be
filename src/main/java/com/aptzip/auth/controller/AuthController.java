package com.aptzip.auth.controller;

import com.aptzip.auth.dto.request.SigninRequest;
import com.aptzip.auth.dto.response.TokenResponse;
import com.aptzip.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signin(@RequestBody SigninRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tokens")
    public ResponseEntity<TokenResponse> reissue(
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletRequest request
    ) {
        String userUuid = (String) request.getAttribute("userUuid");
        TokenResponse response = authService.reissue(userUuid, refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String userUuid = (String) request.getAttribute("userUuid");
        authService.logout(userUuid);
        return ResponseEntity.ok().build();
    }
}
