package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.RefreshTokenDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import com.alphacode.alphacodeapi.mapper.RefreshTokenMapper;
import com.alphacode.alphacodeapi.repository.RefreshTokenRepository;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    public RefreshToken createRefreshToken(Account account) {
        // Tạo token string bằng JwtUtil
        String token = jwtUtil.generateRefreshToken(account);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .accountId(account.getId())
                .expiredAt(LocalDateTime.now().plusSeconds(refreshTokenExpirationMs / 1000)) // convert ms -> sec
                .createAt(LocalDateTime.now())
                .isActive(true)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}