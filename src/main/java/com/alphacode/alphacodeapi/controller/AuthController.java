package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public LoginDto.LoginResponse login(@RequestBody LoginDto.LoginRequest loginRequest) {
        return service.login(loginRequest);
    }
}
