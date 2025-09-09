package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AccountService {
    PagedResult<AccountDto> getAll(int page, int size, Integer status);

    AccountDto getById(UUID id);

    AccountDto create(AccountDto accountDto, MultipartFile avatarFile);

    AccountDto update(UUID id, AccountDto accountDto);

    AccountDto updateProfile(UUID id, AccountDto accountDto, MultipartFile avatarFile);

    AccountDto patchUpdate(UUID id, AccountDto accountDto);

    AccountDto patchUpdateProfile(UUID id, AccountDto accountDto, MultipartFile avatarFile);

    AccountDto changePassword(UUID id, String oldPassword, String newPassword);

    AccountDto changeStatus(UUID id, Integer status);

    String delete(UUID id);

    AccountDto findAccountByFullName(String fullName);
}
