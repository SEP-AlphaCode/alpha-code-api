package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.service.AccountService;
import com.alphacode.alphacodeapi.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public PagedResult<AccountDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/full-name")
    public AccountDto findAccountByFullName(@RequestParam String fullName) {
        return service.findAccountByFullName(fullName);
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AccountDto create(
            @RequestBody AccountDto accountDto,
            @RequestPart(value = "avatarFile") MultipartFile avatarFile) {
        return service.create(accountDto, avatarFile);
    }

    @PutMapping("/{id}")
    public AccountDto update(@PathVariable UUID id, @RequestBody AccountDto dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
