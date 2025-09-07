package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.exception.AuthenticationException;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.service.DashboardService;
import com.alphacode.alphacodeapi.service.RedisRefreshTokenService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository repository;
//    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisRefreshTokenService redisRefreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DashboardService dashboardService;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    @Transactional
    public LoginDto.LoginResponse login(LoginDto.LoginRequest loginRequest) {
        Optional<Account> accountOptional = repository.findAccountByUsername(loginRequest.getUsername());
        if (accountOptional.isEmpty()) {
            accountOptional = repository.findByEmail(loginRequest.getUsername());
        }

        // If no account is found, throw AuthenticationException
        Account account = accountOptional.orElseThrow(() ->
                new AuthenticationException("Invalid username or email"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (account.getStatus() == 0) {
            return LoginDto.LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
        }

        // Generate JWT token and return response
        String accessToken = jwtUtil.generateAccessToken(account);
        String refreshToken = jwtUtil.generateRefreshToken(account);

        // Lưu refresh token vào Redis
        redisRefreshTokenService.save(
                account.getId(),
                refreshToken,
                refreshTokenExpirationMs,
                TimeUnit.MILLISECONDS
        );

        dashboardService.addOnlineUser(account.getId());

        return LoginDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public LoginDto.LoginResponse googleLogin(String request) {
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(request);

            String email = firebaseToken.getEmail();
            String name = (String) firebaseToken.getClaims().get("name");
//            String sub = payload.getSubject();

            Account account = repository.findByEmail(email).orElse(null);

            // If no account is found, throw AuthenticationException
            if (account == null) {
                throw new AuthenticationException("Your account does not have permission to access this application" +
                        ". Please contact the administrator.");
            }

            String accessToken = jwtUtil.generateAccessToken(account);
            String refreshToken = jwtUtil.generateRefreshToken(account);

            // Lưu refresh token vào Redis
            redisRefreshTokenService.save(
                    account.getId(),
                    refreshToken,
                    refreshTokenExpirationMs,
                    TimeUnit.MILLISECONDS
            );

            dashboardService.addOnlineUser(account.getId());

            return LoginDto.LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new AuthenticationException("Google login failed: " + e.getMessage());
        }
    }
}
