package com.aptzip.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository {

    private static final String PREFIX = "refresh_token:";
    private final StringRedisTemplate redisTemplate;

    public void save(String userUuid, String refreshToken, Duration ttl) {
        redisTemplate.opsForValue()
                .set(PREFIX + userUuid, refreshToken, ttl);
    }

    public String findByUserUuid(String userUuid) {
        return redisTemplate.opsForValue().get(PREFIX + userUuid);
    }

    public void delete(String userUuid) {
        redisTemplate.delete(PREFIX + userUuid);
    }

    public boolean exists(String userUuid) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + userUuid));
    }
}
