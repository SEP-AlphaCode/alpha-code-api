package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.exception.AuthenticationException;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.AccountMapper;
import com.alphacode.alphacodeapi.mapper.QRCodeMapper;
import com.alphacode.alphacodeapi.repository.AccountRepository;
import com.alphacode.alphacodeapi.repository.RoleRepository;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.util.JwtUtil;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            entity.setCreateDate(LocalDateTime.now());
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

        if (accountDto.getRoleId() != null) {
            Role role = roleRepository.findById(accountDto.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            existingAccount.setRole(role);
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
            Role role = roleRepository.findById(accountDto.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            existingAccount.setRole(role);
        }
        existingAccount.setLastUpdate(LocalDateTime.now());

        Account updatedEntity = repository.save(existingAccount);
        return AccountMapper.toDto(updatedEntity);

    }

    @Override
    public String delete(UUID id) {
        try{
            Account account = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

//        repository.delete(id);
            account.setStatus(0);
            account.setLastUpdate(LocalDateTime.now());
            repository.save(account);
            return "Xoá thành công Account với ID: " + id;
        }
        catch (Exception e) {
            throw new RuntimeException("Lỗi khi xoá Account", e);
        }

    }

    @Override
    public AccountDto findAccountByFullName(String fullName) {
        var account = repository.findAccountByFullName(fullName);
        return AccountMapper.toDto(account);
    }


}
