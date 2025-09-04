package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findRefreshTokenByTokenAndIsActive(String token, Boolean isActive);

}
