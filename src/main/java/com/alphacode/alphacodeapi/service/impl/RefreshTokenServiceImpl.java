package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.repository.RefreshTokenRepository;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Account account) {
        // Tạo token string bằng JwtUtil
        String token = jwtUtil.generateRefreshToken(account);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .accountId(account.getId())
                .expiredAt(LocalDateTime.now().plusSeconds(refreshTokenExpirationMs / 1000)) // convert ms -> sec
                .createdDate(LocalDateTime.now())
                .isActive(true)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
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
        var account = accountRepository.getReferenceById(accountId);

        // 3. Generate new token
        String newAccessToken = jwtUtil.generateAccessToken(account);
        String newRefreshToken = jwtUtil.generateRefreshToken(account);

        // 4. Remove old refresh token and save new one
        refreshTokenRepository.delete(token.get());
        RefreshToken item = new RefreshToken();
        item.setToken(newRefreshToken);
        item.setAccountId(account.getId());
        item.setIsActive(true);
        item.setCreatedDate(LocalDateTime.now());
        item.setExpiredAt(LocalDateTime.now().plusSeconds(jwtUtil.getRefreshTokenExpirationMs() / 1000)); // convert ms -> sec
        refreshTokenRepository.save(item);

        return LoginDto.LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    @Transactional
    public String logout(String refreshToken) {
        var token = refreshTokenRepository.findRefreshTokenByTokenAndIsActive(refreshToken, true);
        if (token.isEmpty()) {
            throw new RuntimeException("Refresh token is not valid or has expired");
        }

        refreshTokenRepository.delete(token.get());

        return "Logout successful";
    }


}