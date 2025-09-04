package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ExpressionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ExpressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expressions")
@RequiredArgsConstructor
@Tag(name = "Expressions")
public class ExpressionController {

    private final ExpressionService service;

    @GetMapping
    @Operation(summary = "Get all expressions with pagination and optional status filter")
    public PagedResult<ExpressionDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expression by id")
    public ExpressionDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Create new expression")
    public ExpressionDto create(@Valid @RequestBody ExpressionDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update expression by id")
    public ExpressionDto update(@PathVariable UUID id, @Valid @RequestBody ExpressionDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update expression by id")
    public ExpressionDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody ExpressionDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete expression by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
