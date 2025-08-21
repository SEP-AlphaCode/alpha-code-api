package com.alphacode.alphacodeapi.controller;


import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RefreshTokenDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.RefreshToken;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/refresh-tokens")
@RequiredArgsConstructor
@Tag(name = "RefreshTokens")
public class RefreshTokenController {

    private final RefreshTokenService service;

    @PostMapping
    public RefreshToken create(@RequestBody Account account) {
        return service.createRefreshToken(account);
    }
}