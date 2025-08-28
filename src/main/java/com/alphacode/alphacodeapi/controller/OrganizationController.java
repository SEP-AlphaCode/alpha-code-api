package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations")
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping
    @Operation(summary = "Get all organizations with pagination and optional status filter")
    public PagedResult<OrganizationDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get organization by id")
    public OrganizationDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new organization")
    public OrganizationDto create(@RequestBody OrganizationDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update organization by id")
    public OrganizationDto update(@PathVariable UUID id, @RequestBody OrganizationDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update organization by id")
    public OrganizationDto patchUpdate(@PathVariable UUID id, @RequestBody OrganizationDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete organization by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}