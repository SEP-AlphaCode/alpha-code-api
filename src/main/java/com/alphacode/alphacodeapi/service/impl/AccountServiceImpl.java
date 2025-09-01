package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.exception.AuthenticationException;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.AccountMapper;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.repository.RoleRepository;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.util.EmailBody;
import com.alphacode.alphacodeapi.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final RoleRepository roleRepository;
    private final S3ServiceImpl s3Service;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${web-base-url}")
    private String webBaseUrl;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public PagedResult<AccountDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Account> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }
        return new PagedResult<>(pageResult.map(AccountMapper::toDto));
    }

    @Override
    public AccountDto getById(UUID id) {
        var account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto create(AccountDto accountDto, MultipartFile avatarFile) {
        try {
            if (repository.existsByUsername(accountDto.getUsername())) {
                throw new AuthenticationException("Username is already taken");
            }
            if (repository.existsByEmail(accountDto.getEmail())) {
                throw new AuthenticationException("Email is already registered");
            }

            Account entity = AccountMapper.toEntity(accountDto);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setStatus(1);
            entity.setBannedReason(null);
            entity.setPassword(passwordEncoder.encode(accountDto.getPassword()));

            if (accountDto.getRoleId() != null) {
                Role role = roleRepository.findById(accountDto.getRoleId())
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
                entity.setRole(role);
            }

            if (avatarFile != null && !avatarFile.isEmpty()) {
                String fileKey = "avatars/" + System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
                String avatarUrl = s3Service.uploadBytes(avatarFile.getBytes(), fileKey, avatarFile.getContentType());
                entity.setImage(avatarUrl);
            }

            Account savedEntity = repository.save(entity);
            return AccountMapper.toDto(savedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo Account", e);
        }
    }

    @Override
    public AccountDto update(UUID id, AccountDto accountDto) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        existingAccount.setUsername(accountDto.getUsername());
        existingAccount.setPassword(accountDto.getPassword());
        existingAccount.setFullName(accountDto.getFullName());
        existingAccount.setPhone(accountDto.getPhone());
        existingAccount.setGender(accountDto.getGender());
        existingAccount.setLastUpdate(LocalDateTime.now());
        existingAccount.setEmail(accountDto.getEmail());
        existingAccount.setRoleId(accountDto.getRoleId());

        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);
    }

    @Override
    public AccountDto updateProfile(UUID id, AccountDto accountDto, MultipartFile avatarFile) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        existingAccount.setUsername(accountDto.getUsername());
        existingAccount.setPassword(accountDto.getPassword());
        existingAccount.setFullName(accountDto.getFullName());
        existingAccount.setPhone(accountDto.getPhone());
        existingAccount.setGender(accountDto.getGender());
        existingAccount.setLastUpdate(LocalDateTime.now());
        existingAccount.setEmail(accountDto.getEmail());

        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String fileKey = "avatars/" + System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
                String avatarUrl = s3Service.uploadBytes(avatarFile.getBytes(), fileKey, avatarFile.getContentType());
                existingAccount.setImage(avatarUrl);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi tải lên ảnh đại diện", e);
            }

        }
        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);
    }

    @Override
    public AccountDto patchUpdate(UUID id, AccountDto accountDto) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (accountDto.getUsername() != null) {
            existingAccount.setUsername(accountDto.getUsername());
        }
        if (accountDto.getPassword() != null) {
            existingAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        }
        if (accountDto.getFullName() != null) {
            existingAccount.setFullName(accountDto.getFullName());
        }
        if (accountDto.getPhone() != null) {
            existingAccount.setPhone(accountDto.getPhone());
        }
        if (accountDto.getGender() != null) {
            existingAccount.setGender(accountDto.getGender());
        }
        if (accountDto.getEmail() != null) {
            existingAccount.setEmail(accountDto.getEmail());
        }
        if (accountDto.getRoleId() != null) {
            existingAccount.setRoleId(accountDto.getRoleId());
        }
        existingAccount.setLastUpdate(LocalDateTime.now());

        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);

    }

    @Override
    public AccountDto patchUpdateProfile(UUID id, AccountDto accountDto, MultipartFile avatarFile) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (accountDto.getUsername() != null) {
            existingAccount.setUsername(accountDto.getUsername());
        }
        if (accountDto.getPassword() != null) {
            existingAccount.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        }
        if (accountDto.getFullName() != null) {
            existingAccount.setFullName(accountDto.getFullName());
        }
        if (accountDto.getPhone() != null) {
            existingAccount.setPhone(accountDto.getPhone());
        }
        if (accountDto.getGender() != null) {
            existingAccount.setGender(accountDto.getGender());
        }
        if (accountDto.getEmail() != null) {
            existingAccount.setEmail(accountDto.getEmail());
        }
        if (accountDto.getRoleId() != null) {
            Role role = roleRepository.findById(accountDto.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            existingAccount.setRole(role);
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String fileKey = "avatars/" + System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
                String avatarUrl = s3Service.uploadBytes(avatarFile.getBytes(), fileKey, avatarFile.getContentType());
                existingAccount.setImage(avatarUrl);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi tải lên ảnh đại diện", e);
            }

        }

        existingAccount.setLastUpdate(LocalDateTime.now());

        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);
    }

    @Override
    public AccountDto changePassword(UUID id, String oldPassword, String newPassword) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!passwordEncoder.matches(oldPassword, existingAccount.getPassword())) {
            throw new AuthenticationException("Old password is incorrect");
        }

        existingAccount.setPassword(passwordEncoder.encode(newPassword));
        existingAccount.setLastUpdate(LocalDateTime.now());
        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);
    }

    @Override
    public AccountDto changeStatus(UUID id, Integer status) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        existingAccount.setStatus(status);
        existingAccount.setLastUpdate(LocalDateTime.now());
        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);
    }

    @Override
    public String delete(UUID id) {
        try {
            Account account = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

//        repository.delete(id);
            account.setStatus(0);
            account.setLastUpdate(LocalDateTime.now());
            repository.save(account);
            return "Xoá thành công Account với ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xoá Account", e);
        }

    }

    @Override
    public AccountDto findAccountByFullName(String fullName) {
        var account = repository.findAccountByFullName(fullName);
        return AccountMapper.toDto(account);
    }

    @Override
    public boolean requestResetPassword(String email) throws MessagingException {
        var account = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found or failed to send mail!"));

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

        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
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
