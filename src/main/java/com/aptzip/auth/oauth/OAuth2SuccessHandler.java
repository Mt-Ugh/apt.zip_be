package com.aptzip.auth.oauth;

import com.aptzip.auth.repository.RedisRefreshTokenRepository;
import com.aptzip.common.config.jwt.TokenProvider;
import com.aptzip.common.config.jwt.JwtProperties;
import com.aptzip.common.util.CookieUtil;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;
    private final UserRepository userRepository;
    private final String redirectUri;

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public OAuth2SuccessHandler(TokenProvider tokenProvider,
                                JwtProperties jwtProperties,
                                RedisRefreshTokenRepository redisRefreshTokenRepository,
                                UserRepository userRepository,
                                String redirectUri) {
        this.tokenProvider = tokenProvider;
        this.jwtProperties = jwtProperties;
        this.redisRefreshTokenRepository = redisRefreshTokenRepository;
        this.userRepository = userRepository;
        this.redirectUri = redirectUri;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("리디렉션 URI: {}", redirectUri);
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
        String accessToken = tokenProvider.generateToken(user, Duration.ofMinutes(jwtProperties.getAccessTokenExpirationMinutes()));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays()));

        // 4. Redis에 RefreshToken 저장
        Duration refreshTokenExpiry = Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays());
        redisRefreshTokenRepository.save(user.getUserUuid(), refreshToken, refreshTokenExpiry);

        // 5. 쿠키에 RefreshToken 저장
        CookieUtil.addHttpOnlyCookie(
                response,
                REFRESH_TOKEN_COOKIE_NAME,
                refreshToken,
                (int) refreshTokenExpiry.getSeconds()
        );

        // 6. AccessToken을 쿼리로 포함해 클라이언트로 리다이렉트
        String redirectUrl = redirectUri + "?accessToken=" + accessToken;
        response.sendRedirect(redirectUrl);
    }

}
