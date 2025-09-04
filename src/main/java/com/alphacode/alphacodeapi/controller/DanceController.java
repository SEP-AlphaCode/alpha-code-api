package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.DanceService;
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
    public DanceDto create(@Valid @RequestBody DanceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update dance by id")
    public DanceDto update(@PathVariable UUID id, @Valid @RequestBody DanceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update dance by id")
    public DanceDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody DanceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete dance by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}