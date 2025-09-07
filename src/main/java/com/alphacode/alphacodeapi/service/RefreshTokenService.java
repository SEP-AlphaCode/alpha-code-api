package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    String createRefreshToken(UUID userId, String refreshToken);
    boolean validateRefreshToken(UUID userId, String refreshToken);
    void deleteRefreshToken(UUID userId);
    String getRefreshToken(UUID userId);
    LoginDto.LoginResponse refreshNewToken(String refreshToken);
    String logout(String refreshToken);
}
