package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityStepService;
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
    public PagedResult<ActivityStepDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "activityId", required = false) UUID activityId) {
        return service.getAll(page, size, activityId);
    }

    @GetMapping("/{id}")
    public ActivityStepDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public ActivityStepDto create(@RequestBody ActivityStepDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ActivityStepDto update(@PathVariable UUID id, @RequestBody ActivityStepDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ActivityStepDto patchUpdate(@PathVariable UUID id, @RequestBody ActivityStepDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
