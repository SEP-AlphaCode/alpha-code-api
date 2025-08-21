package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.LoginDto;

public interface AuthService {
    LoginDto.LoginResponse login(LoginDto.LoginRequest loginRequest);
}
