package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    @Operation(summary = "Update activity by id")
    public ActivityDto update(@PathVariable UUID id, @Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.update(id, activityDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update activity by id")
    public ActivityDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.patchUpdate(id, activityDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete activity by id")
    public String delete(@PathVariable UUID id) {
        return activityService.delete(id);
    }
}
