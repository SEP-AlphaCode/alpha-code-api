package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import com.alphacode.alphacodeapi.dto.ResetPasswordRequestDto;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService service;
    private final AccountService accountService;
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

    @PostMapping("/reset-password/request")
    @Operation(summary = "Request reset password")
    public ResponseEntity<String> requestResetPassword(@RequestBody ResetPasswordRequestDto request) throws MessagingException {
        System.out.println("Incoming email = " + request.getEmail());
        boolean success = accountService.requestResetPassword(request.getEmail());
        System.out.println("Result from service = " + success);
        return success ? ResponseEntity.ok("Reset password link sent to email")
                : ResponseEntity.badRequest().body("Email not found or failed to send mail");
    }

    @PostMapping("/reset-password/reset")
    @Operation(summary = "Reset the password")
    public ResponseEntity<String> confirmResetPassword(@RequestBody ResetPassworDto dto) {
        boolean success = accountService.confirmResetPassword(dto);
        return success ? ResponseEntity.ok("Password reset successful")
                : ResponseEntity.badRequest().body("Token is invalid or expired");
    }
}
