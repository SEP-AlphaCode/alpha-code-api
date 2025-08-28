package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityStepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activity-steps")
@RequiredArgsConstructor
@Tag(name = "ActivitySteps")
public class ActivityStepController {

    private final ActivityStepService service;

    @GetMapping
    @Operation(summary = "Get all activity steps with pagination and optional activityId filter")
    public PagedResult<ActivityStepDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "activityId", required = false) UUID activityId) {
        return service.getAll(page, size, activityId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get activity step by id")
    public ActivityStepDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new activity step")
    public ActivityStepDto create(@RequestBody ActivityStepDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update activity step by id")
    public ActivityStepDto update(@PathVariable UUID id, @RequestBody ActivityStepDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update activity step by id")
    public ActivityStepDto patchUpdate(@PathVariable UUID id, @RequestBody ActivityStepDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete activity step by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
