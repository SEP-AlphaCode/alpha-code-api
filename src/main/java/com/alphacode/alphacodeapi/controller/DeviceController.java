package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.DeviceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
@Tag(name = "Devices")
public class DeviceController {

    private final DeviceService service;

    @GetMapping
    @Operation(summary = "Get all devices with pagination and optional spaceId and status filter")
    public PagedResult<DeviceDto> getAll(
            @RequestParam(value = "spaceId", required = false) UUID spaceId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(spaceId, page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by id")
    public DeviceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new device")
    public DeviceDto create(@RequestBody DeviceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update device by id")
    public DeviceDto update(@PathVariable UUID id, @Valid @RequestBody DeviceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update device by id")
    public DeviceDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody DeviceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete device by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
