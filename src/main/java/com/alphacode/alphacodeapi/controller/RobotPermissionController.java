package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotPermissionDto;
import com.alphacode.alphacodeapi.service.RobotPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @Operation(summary = "Create new robot permission")
    public RobotPermissionDto create(@Valid @RequestBody RobotPermissionDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update robot permission by id")
    public RobotPermissionDto update(@PathVariable UUID id, @Valid @RequestBody RobotPermissionDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update robot permission by id")
    public RobotPermissionDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody RobotPermissionDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete robot permission by id (soft delete)")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
