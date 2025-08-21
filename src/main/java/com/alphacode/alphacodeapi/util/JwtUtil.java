package com.alphacode.alphacodeapi.util;

import com.alphacode.alphacodeapi.entity.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private int jwtExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private int refreshTokenExpirationMs;

    // Generate a secure key from the secret string
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Account account) {
        return Jwts.builder()
                .subject(account.getUsername())
                .claims(Map.of(
                        "id", account.getId(),
                        "fullName", account.getFullName(),
                        "username", account.getUsername(),
                        "email", account.getEmail(),
                        "roleId", account.getRoleId(),
                        "roleName", account.getRole().getName()
                ))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // vd: 15 phút
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Integer getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }

    public String generateRefreshToken(Account account) {
        return Jwts.builder()
                .subject(account.getUsername())
                .claims(Map.of(
                        "id", account.getId(),
                        "fullName", account.getFullName(),
                        "username", account.getUsername(),
                        "email", account.getEmail(),
                        "roleId", account.getRoleId(),
                        "roleName", account.getRole().getName()
                ))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs)) // vd: 7 ngày
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }


    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log error if needed
            return false;
        }
    }
}