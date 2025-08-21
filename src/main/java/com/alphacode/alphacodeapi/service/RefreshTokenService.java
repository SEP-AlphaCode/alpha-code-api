package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.RefreshTokenDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Account account);
//    Optional<RefreshToken> findByToken(String token);
//    boolean verifyExpiration(RefreshToken token);
}
