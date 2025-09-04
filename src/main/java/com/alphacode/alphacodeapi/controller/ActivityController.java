package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Tag(name = "Activities")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping
    @Operation(summary = "Get all activities with pagination and optional status filter")
    public PagedResult<ActivityDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return activityService.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get activity by id")
    public ActivityDto getById(@PathVariable UUID id) {
        return activityService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new activity")
    public ActivityDto create(@Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.create(activityDto);
    }

    @GetMapping("get-by-qr-code/{qrCode}")
    @Operation(summary = "Get activity by QR code")
    public ActivityDto getByQRCode(@PathVariable String qrCode) {
        return activityService.getByQRCode(qrCode);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update activity by id")
    public ActivityDto update(@PathVariable UUID id, @Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.update(id, activityDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update activity by id")
    public ActivityDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.patchUpdate(id, activityDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete activity by id")
    public String delete(@PathVariable UUID id) {
        return activityService.delete(id);
    }
}
