package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.LoginDto;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.exception.AuthenticationException;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.service.AuthService;
import com.alphacode.alphacodeapi.service.DashboardService;
import com.alphacode.alphacodeapi.service.RedisRefreshTokenService;
import com.alphacode.alphacodeapi.util.EmailBody;
import com.alphacode.alphacodeapi.util.JwtUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository repository;
//    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisRefreshTokenService redisRefreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final DashboardService dashboardService;
    @Value("${web-base-url}")
    private String webBaseUrl;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;

    @Override
    @Transactional
    public LoginDto.LoginResponse login(LoginDto.LoginRequest loginRequest) {
        Optional<Account> accountOptional = repository.findAccountByUsername(loginRequest.getUsername());
        if (accountOptional.isEmpty()) {
            accountOptional = repository.findByEmail(loginRequest.getUsername());
        }

        // If no account is found, throw AuthenticationException
        Account account = accountOptional.orElseThrow(() ->
                new AuthenticationException("Invalid username or email"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (account.getStatus() == 0) {
            return LoginDto.LoginResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .build();
        }

        // Generate JWT token and return response
        String accessToken = jwtUtil.generateAccessToken(account);
        String refreshToken = jwtUtil.generateRefreshToken(account);

        // Lưu refresh token vào Redis
        redisRefreshTokenService.save(
                account.getId(),
                refreshToken,
                refreshTokenExpirationMs,
                TimeUnit.MILLISECONDS
        );

        dashboardService.addOnlineUser(account.getId());

        return LoginDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public LoginDto.LoginResponse googleLogin(String request) {
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(request);

            String email = firebaseToken.getEmail();
            String name = (String) firebaseToken.getClaims().get("name");
//            String sub = payload.getSubject();

            Account account = repository.findByEmail(email).orElse(null);

            // If no account is found, throw AuthenticationException
            if (account == null) {
                throw new AuthenticationException("Your account does not have permission to access this application" +
                        ". Please contact the administrator.");
            }

            String accessToken = jwtUtil.generateAccessToken(account);
            String refreshToken = jwtUtil.generateRefreshToken(account);

            // Lưu refresh token vào Redis
            redisRefreshTokenService.save(
                    account.getId(),
                    refreshToken,
                    refreshTokenExpirationMs,
                    TimeUnit.MILLISECONDS
            );

            dashboardService.addOnlineUser(account.getId());

            return LoginDto.LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new AuthenticationException("Google login failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean requestResetPassword(String email) throws MessagingException {
        var account = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found!"));

        System.out.println("Account get = " + account.getId());

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Đặt lại mật khẩu - AlphaCode");

        var resetToken = jwtUtil.generateResetPasswordToken(account);
        var resetLink = webBaseUrl + "/reset-password/reset?token=" + resetToken;

        var emailBody = EmailBody.getResetPasswordEmailBody(account.getFullName(), resetLink);

        helper.setText(emailBody, true);

        // Put logo picture (inline image với cid:alphacode-logo)
        ClassPathResource logoImage = new ClassPathResource("static/images/alphacode-logo.png");
        helper.addInline("alphacode-logo", logoImage);

        mailSender.send(message);
        return true;
    }

    @Override
    @Transactional
    public boolean confirmResetPassword(ResetPassworDto dto) {
        String email = jwtUtil.extractEmail(dto.getResetToken());

        Account account = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid reset token"));

        // 2. Check token is valid or not
        if (!jwtUtil.validateJwtToken(dto.getResetToken())) {
            throw new IllegalArgumentException("Reset token is invalid or expired");
        }

        // 3. Hash new password
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        // 4. Save new password to db
        repository.save(account);
        return true;
    }
}
