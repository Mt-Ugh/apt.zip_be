package com.aptzip.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository {

    private static final String PREFIX = "refresh_token:";
    private final RedisTemplate<String, String> redisTemplate;

    // RefreshToken 저장
    public void save(String userUuid, String refreshToken, Duration ttl) {
        redisTemplate.opsForValue().set(PREFIX + userUuid, refreshToken, ttl);
    }

    // RefreshToken 조회
    public String findByUserUuid(String userUuid) {
        return redisTemplate.opsForValue().get(PREFIX + userUuid);
    }

    // RefreshToken 삭제
    public void delete(String userUuid) {
        redisTemplate.delete(PREFIX + userUuid);
    }

    // RefreshToken 존재 여부 확인
    public boolean exists(String userUuid) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + userUuid));
    }
}
