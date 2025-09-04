package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.SpaceDto;
import com.alphacode.alphacodeapi.service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spaces")
@RequiredArgsConstructor
@Tag(name = "Spaces")
public class SpaceController {

    private final SpaceService service;

    @GetMapping
    @Operation(summary = "Get all spaces with pagination")
    public PagedResult<SpaceDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get space by id")
    public SpaceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new space")
    public SpaceDto create(@Valid @RequestBody SpaceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update space by id")
    public SpaceDto update(@PathVariable UUID id, @Valid @RequestBody SpaceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update space by id")
    public SpaceDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody SpaceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete space by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
