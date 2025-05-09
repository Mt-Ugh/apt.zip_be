package com.aptzip.auth.service;

import com.aptzip.auth.dto.request.SigninRequest;
import com.aptzip.auth.dto.response.TokenResponse;
import com.aptzip.auth.repository.RedisRefreshTokenRepository;
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

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(14);

    // ✅ 일반 로그인
    @Transactional
    public TokenResponse login(SigninRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken();

        refreshTokenRepository.save(user.getUserUuid(), refreshToken, REFRESH_TOKEN_TTL);

        return new TokenResponse(accessToken, refreshToken);
    }

    // ✅ 로그아웃 (Redis에서 RefreshToken 삭제)
    public void logout(String userUuid) {
        refreshTokenRepository.delete(userUuid);
    }

    // ✅ 토큰 재발급
    public TokenResponse reissue(String userUuid, String refreshToken) {
        String stored = refreshTokenRepository.findByUserUuid(userUuid);

        if (stored == null || !stored.equals(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String newAccessToken = tokenProvider.createAccessToken(user);
        String newRefreshToken = tokenProvider.createRefreshToken();

        refreshTokenRepository.save(user.getUserUuid(), newRefreshToken, REFRESH_TOKEN_TTL);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
