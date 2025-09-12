package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotPermissionDto;
import com.alphacode.alphacodeapi.service.RobotPermissionService;
import com.alphacode.alphacodeapi.validation.OnCreate;
import com.alphacode.alphacodeapi.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/robot-permissions")
@RequiredArgsConstructor
@Tag(name = "RobotPermissions")
public class RobotPermissionController {
    private final RobotPermissionService service;

    @GetMapping
    @Operation(summary = "Get all robot permissions with pagination and optional status filter")
    public PagedResult<RobotPermissionDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get robot permission by id")
    public RobotPermissionDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Create new robot permission")
    public RobotPermissionDto create(@Validated(OnCreate.class) @RequestBody RobotPermissionDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update robot permission by id")
    public RobotPermissionDto update(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody RobotPermissionDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update robot permission by id")
    public RobotPermissionDto patchUpdate(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody RobotPermissionDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete robot permission by id (soft delete)")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
