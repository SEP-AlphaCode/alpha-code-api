package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.dto.RefreshTokenDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import com.alphacode.alphacodeapi.mapper.AccountMapper;
import com.alphacode.alphacodeapi.mapper.RefreshTokenMapper;
import com.alphacode.alphacodeapi.repository.RefreshTokenRepository;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

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

    @Override
    public Optional<RefreshToken> findByTokenAndIsActive(String token, Boolean isActive) {
        return refreshTokenRepository.findRefreshTokenByTokenAndIsActive(token, isActive);
    }


    public LoginDto.LoginResponse refreshNewToken(String refreshToken) {
        var token = refreshTokenRepository.findRefreshTokenByTokenAndIsActive(refreshToken, true);
        if (token.isEmpty()) {
            throw new RuntimeException("Refresh token is not valid or has expired");
        }

        // 2. Decode JWT to take account infomation
        UUID accountId = jwtUtil.extractClaim(refreshToken, claims -> UUID.fromString(claims.get("id", String.class)));
        var account = accountService.getById(accountId);

        // 3. Generate new token
        String newAccessToken = jwtUtil.generateAccessToken(AccountMapper.toEntity(account));
        String newRefreshToken = jwtUtil.generateRefreshToken(AccountMapper.toEntity(account));

        // 4. Remove old refresh token and save new one
        refreshTokenRepository.deleteByToken(refreshToken);
        return new LoginDto.LoginResponse(newAccessToken, newRefreshToken);
    }
}