package com.alphacode.alphacodeapi.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface RedisRefreshTokenService {
    void save(UUID userId, String refreshToken, long duration, TimeUnit unit);
    String get(UUID userId);
    void delete(UUID userId);
    boolean validate(UUID userId, String refreshToken);
}
