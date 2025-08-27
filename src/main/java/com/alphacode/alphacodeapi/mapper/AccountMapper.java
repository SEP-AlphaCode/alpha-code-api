package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.entity.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountMapper {
    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setPassword(account.getPassword());
        dto.setFullName(account.getFullName());
        dto.setPhone(account.getPhone());
        dto.setGender(account.getGender());
        dto.setCreatedDate(account.getCreatedDate());
        dto.setLastEdited(account.getLastUpdate());
        dto.setBannedReason(account.getBannedReason());
        dto.setRoleId(account.getRoleId());

        if (account.getRole() != null) {
            dto.setRoleName(account.getRole().getName());
        }

        dto.setStatus(account.getStatus());
        dto.setImage(account.getImage());
        dto.setEmail(account.getEmail());
        return dto;
    }

    public static Account toEntity(AccountDto dto) {
        if (dto == null) {
            return null;
        }
        Account account = new Account();
        account.setId(dto.getId());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setFullName(dto.getFullName());
        account.setPhone(dto.getPhone());
        account.setGender(dto.getGender());
        account.setCreatedDate(dto.getCreatedDate());
        account.setLastUpdate(dto.getLastEdited());
        account.setImage(dto.getImage());
        account.setBannedReason(dto.getBannedReason());
        account.setRoleId(dto.getRoleId());
        account.setStatus(dto.getStatus());
        account.setEmail(dto.getEmail());
        return account;
    }
}

