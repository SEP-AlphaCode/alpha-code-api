package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.dto.ResetPassworDto;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts")
public class AccountController {

    private final AccountService service;

    @GetMapping
    @Operation(summary = "Get all accounts with pagination and optional status filter")
    public PagedResult<AccountDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/full-name")
    @Operation(summary = "Find account by full name")
    public AccountDto findAccountByFullName(@RequestParam String fullName) {
        return service.findAccountByFullName(fullName);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by id")
    public AccountDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create new account")
    public AccountDto create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("gender") Integer gender,
            @RequestParam("roleId") UUID roleId,
            @RequestPart(value = "avatarFile") MultipartFile avatarFile) {

        AccountDto accountDto = new AccountDto();
        accountDto.setUsername(username);
        accountDto.setPassword(password);
        accountDto.setFullName(fullName);
        accountDto.setPhone(phone);
        accountDto.setEmail(email);
        accountDto.setGender(gender);
        accountDto.setRoleId(roleId);
        return service.create(accountDto, avatarFile);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update account by id")
    public AccountDto update(@PathVariable UUID id, @RequestBody AccountDto dto) {
        return service.update(id, dto);
    }

    @PutMapping(value = "/{id}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update account profile")
    public AccountDto updateProfile(
            @PathVariable UUID id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("gender") Integer gender,
            @RequestParam("roleId") UUID roleId,
            @RequestPart(value = "avatarFile") MultipartFile avatarFile) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(id);
        accountDto.setUsername(username);
        accountDto.setPassword(password);
        accountDto.setFullName(fullName);
        accountDto.setPhone(phone);
        accountDto.setEmail(email);
        accountDto.setGender(gender);
        accountDto.setRoleId(roleId);
        return service.updateProfile(id, accountDto, avatarFile);
    }

    @PatchMapping(value = "/{id}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Patch update account profile")
    public AccountDto patchUpdateProfile(
            @PathVariable UUID id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "gender", required = false) Integer gender,
            @RequestParam(value = "roleId", required = false) UUID roleId,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        AccountDto accountDto = new AccountDto();
        if (username != null) {
            accountDto.setUsername(username);
        }
        if (password != null) {
            accountDto.setPassword(password);
        }
        if (fullName != null) {
            accountDto.setFullName(fullName);
        }
        if (phone != null) {
            accountDto.setPhone(phone);
        }
        if (email != null) {
            accountDto.setEmail(email);
        }
        if (gender != null) {
            accountDto.setGender(gender);
        }
        if (roleId != null) {
            accountDto.setRoleId(roleId);
        }
        return service.patchUpdateProfile(id, accountDto, avatarFile);
    }

    @PostMapping("/reset-password/request")
    @Operation(summary = "Request reset password")
    public ResponseEntity<String> requestResetPassword(@RequestBody String email) throws MessagingException {
        boolean success = service.requestResetPassword(email);
        return success ? ResponseEntity.ok("Reset password link sent to email")
                : ResponseEntity.badRequest().body("Email not found or failed to send mail");
    }

    @PostMapping("/reset-password/reset")
    @Operation(summary = "Reset the password")
    public ResponseEntity<String> confirmResetPassword(@RequestBody ResetPassworDto dto) {
        boolean success = service.confirmResetPassword(dto);
        return success ? ResponseEntity.ok("Password reset successful")
                : ResponseEntity.badRequest().body("Token is invalid or expired");
    }


    @PatchMapping("/{id}")
    @Operation(summary = "Patch update account by id")
    public AccountDto patchUpdate(@PathVariable UUID id, @RequestBody AccountDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }

    @PatchMapping("/{id}/change-password")
    @Operation(summary = "Change password")
    public AccountDto changePassword(@PathVariable UUID id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return service.changePassword(id, oldPassword, newPassword);
    }

    @PatchMapping("/{id}/change-status")
    @Operation(summary = "Change account status")
    public AccountDto changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }
}
