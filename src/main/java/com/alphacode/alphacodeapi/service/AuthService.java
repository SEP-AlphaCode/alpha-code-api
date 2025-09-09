package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import jakarta.mail.MessagingException;

public interface AuthService {
    LoginDto.LoginResponse login(LoginDto.LoginRequest loginRequest);

    LoginDto.LoginResponse googleLogin(String request);

    boolean requestResetPassword(String email) throws MessagingException;

    boolean confirmResetPassword(ResetPassworDto dto);
}
