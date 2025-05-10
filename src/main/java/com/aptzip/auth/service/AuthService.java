package com.aptzip.auth.service;

import com.aptzip.auth.dto.request.SigninRequest;
import com.aptzip.auth.dto.response.TokenResponse;
import com.aptzip.auth.repository.RedisRefreshTokenRepository;
import com.aptzip.common.config.jwt.JwtProperties;
import com.aptzip.common.config.jwt.TokenProvider;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisRefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    // 일반 로그인
    @Transactional
    public TokenResponse login(SigninRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.generateToken(
                user,
                Duration.ofMinutes(jwtProperties.getAccessTokenExpirationMinutes())
        );

        String refreshToken = tokenProvider.generateToken(
                user,
                Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays())
        );

        refreshTokenRepository.save(user.getUserUuid(), refreshToken,
                Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays()));

        return new TokenResponse(accessToken, refreshToken);
    }

    // 로그아웃 (Redis에서 RefreshToken 삭제)
    public void logout(String userUuid) {
        refreshTokenRepository.delete(userUuid);
    }

    // 토큰 재발급
    public TokenResponse reissue(String userUuid, String refreshToken) {
        String stored = refreshTokenRepository.findByUserUuid(userUuid);

        if (stored == null || !stored.equals(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String newAccessToken = tokenProvider.generateToken(
                user,
                Duration.ofMinutes(jwtProperties.getAccessTokenExpirationMinutes())
        );

        String newRefreshToken = tokenProvider.generateToken(
                user,
                Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays())
        );

        refreshTokenRepository.save(user.getUserUuid(), newRefreshToken,
                Duration.ofDays(jwtProperties.getRefreshTokenExpirationDays()));

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
