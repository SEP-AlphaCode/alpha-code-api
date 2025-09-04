package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Login with username and password")
    public LoginDto.LoginResponse login(@RequestBody LoginDto.LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

    @PostMapping("/refresh-new-token")
    @Operation(summary = "Refresh new access token using refresh token")
    public LoginDto.LoginResponse refreshNewToken(@RequestParam String refreshToken) {
        return refreshTokenService.refreshNewToken(refreshToken);
    }

    @PostMapping("/google-login")
    @Operation(summary = "Login with Google ID token")
    public LoginDto.LoginResponse googleLogin(@RequestBody String idToken) {
        return service.googleLogin(idToken);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout and invalidate the refresh token")
    public String logout(@RequestBody String refreshToken) {
        return refreshTokenService.logout(refreshToken);
    }
}
