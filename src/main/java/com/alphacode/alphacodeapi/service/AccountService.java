package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AccountService {
    PagedResult<AccountDto> getAll(int page, int size, Integer status);

    AccountDto getById(UUID id);

    AccountDto create(AccountDto accountDto, MultipartFile avatarFile);

    AccountDto update(UUID id,AccountDto accountDto);

    AccountDto patchUpdate(UUID id, AccountDto accountDto);

    String delete(UUID id);

    AccountDto findAccountByFullName(String fullName);

    boolean requestResetPassword(String email) throws MessagingException ;

    boolean confirmResetPassword(ResetPassworDto dto);
}
