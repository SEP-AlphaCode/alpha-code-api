package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.DanceService;
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
@RequestMapping("/api/v1/dances")
@RequiredArgsConstructor
@Tag(name = "Dances")
public class DanceController {

    private final DanceService service;

    @GetMapping
    @Operation(summary = "Get all dances with pagination and optional status filter")
    public PagedResult<DanceDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search
    ) {
        return service.getPagedDances(page, size, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dance by id")
    public DanceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Create new dance")
    public DanceDto create(@Validated(OnCreate.class) @RequestBody DanceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update dance by id")
    public DanceDto update(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody DanceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update dance by id")
    public DanceDto patchUpdate(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody DanceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Change dance status by id")
    public DanceDto changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return service.changeDanceStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete dance by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}