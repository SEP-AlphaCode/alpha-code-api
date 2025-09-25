package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotDto;
import com.alphacode.alphacodeapi.service.RobotService;
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
@RequestMapping("/api/v1/robots")
@RequiredArgsConstructor
@Tag(name = "Robots")
public class RobotController {
    private final RobotService service;

    @GetMapping
    @Operation(summary = "Get all robots with pagination")
    public PagedResult<RobotDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "organizationId", required = false) UUID organizationId,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, organizationId, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get robot by id")
    public RobotDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Create new robot")
    public RobotDto create(@Validated(OnCreate.class) @RequestBody RobotDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update robot by id")
    public RobotDto update(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody RobotDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update robot by id")
    public RobotDto patchUpdate(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody RobotDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete robot by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
