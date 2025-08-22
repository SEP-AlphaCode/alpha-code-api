package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService service;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public LoginDto.LoginResponse login(@RequestBody LoginDto.LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

    @PostMapping("/refresh-new-token")
    public  LoginDto.LoginResponse refreshNewToken(@RequestParam String refreshToken) {
        return refreshTokenService.refreshNewToken(refreshToken);
    }
}
