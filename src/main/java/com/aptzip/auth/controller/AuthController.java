package com.aptzip.auth.controller;

    import com.aptzip.auth.dto.request.SigninRequest;
import com.aptzip.auth.dto.response.LoginResponse;
import com.aptzip.auth.dto.response.TokenResponse;
import com.aptzip.auth.dto.response.TokenUserResponse;
import com.aptzip.auth.dto.response.TokensResponse;
import com.aptzip.auth.service.AuthService;
import com.aptzip.common.config.jwt.JwtProperties;
import com.aptzip.common.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(
            @RequestBody SigninRequest request,
            HttpServletResponse response
    ) {
        TokenUserResponse tokenResponse = authService.login(request);
        int refreshTokenExpirySeconds = jwtProperties.getRefreshTokenExpirationDays() * 24 * 60 * 60;

        CookieUtil.addHttpOnlyCookie(
                response,
                REFRESH_TOKEN_COOKIE_NAME,
                tokenResponse.refreshToken(),
                refreshTokenExpirySeconds
        );

        return ResponseEntity.ok(new LoginResponse(
                tokenResponse.accessToken(),
                tokenResponse.userUuid(),
                tokenResponse.nickname(),
                tokenResponse.profileUrl()
        ));
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signout(HttpServletRequest request, HttpServletResponse response) {
        String userUuid = (String) request.getAttribute("userUuid");
        authService.logout(userUuid);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tokens")
    public ResponseEntity<TokensResponse> reissueToken(
            @CookieValue(name = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        TokenResponse tokenResponse = authService.reissue(refreshToken);

        CookieUtil.addHttpOnlyCookie(
                response,
                REFRESH_TOKEN_COOKIE_NAME,
                tokenResponse.refreshToken(),
                jwtProperties.getRefreshTokenExpirationDays() * 24 * 60 * 60
        );

        return ResponseEntity.ok(new TokensResponse(tokenResponse.accessToken()));
    }
}
