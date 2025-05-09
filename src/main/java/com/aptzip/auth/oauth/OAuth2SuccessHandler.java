package com.aptzip.auth.oauth;

import com.aptzip.auth.repository.RedisRefreshTokenRepository;
import com.aptzip.common.config.jwt.TokenProvider;
import com.aptzip.common.util.CookieUtil;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final UserRepository userRepository;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    private static final int REFRESH_TOKEN_EXPIRY_SECONDS = 14 * 24 * 60 * 60;
    private static final String REDIRECT_URI = "https://your-client.com/oauth/callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. OAuth2 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "이메일 정보를 찾을 수 없습니다.");
            return;
        }

        // 2. 유저 조회
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유저 정보가 존재하지 않습니다.");
            return;
        }

        User user = optionalUser.get();

        // 3. JWT 발급
        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken();

        // 4. Redis에 RefreshToken 저장
        redisRefreshTokenRepository.save(user.getUserUuid(), refreshToken, Duration.ofSeconds(REFRESH_TOKEN_EXPIRY_SECONDS));

        // 5. 쿠키에 RefreshToken 저장
        CookieUtil.addHttpOnlyCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, REFRESH_TOKEN_EXPIRY_SECONDS);

        // 6. AccessToken을 쿼리로 포함해 클라이언트로 리다이렉트
        String redirectUrl = REDIRECT_URI + "?accessToken=" + accessToken;
        response.sendRedirect(redirectUrl);
    }
}
