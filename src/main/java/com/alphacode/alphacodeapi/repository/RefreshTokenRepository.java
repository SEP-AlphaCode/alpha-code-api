package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findRefreshTokenByTokenAndIsActive(String token, Boolean isActive);
    void deleteByToken(String token);
}
