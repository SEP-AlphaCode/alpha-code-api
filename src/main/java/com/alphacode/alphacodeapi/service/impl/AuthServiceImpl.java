package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import com.alphacode.alphacodeapi.exception.AuthenticationException;
import com.alphacode.alphacodeapi.mapper.AccountMapper;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.repository.RefreshTokenRepository;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
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

        RefreshToken item = new RefreshToken();
        item.setToken(refreshToken);
        item.setAccountId(account.getId());
        item.setIsActive(true);
        item.setCreateAt(LocalDateTime.now());
        item.setExpiredAt(LocalDateTime.now().plusSeconds(jwtUtil.getRefreshTokenExpirationMs() / 1000)); // convert ms -> sec
        refreshTokenRepository.save(item);

        return LoginDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
