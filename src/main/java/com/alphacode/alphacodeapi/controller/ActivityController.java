package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public PagedResult<ActivityDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return activityService.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public ActivityDto getById(@PathVariable UUID id) {
        return activityService.getById(id);
    }

    @PostMapping
    public ActivityDto create(@Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.create(activityDto);
    }

    @PutMapping("/{id}")
    public ActivityDto update(@PathVariable UUID id, @Valid @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.update(id, activityDto);
    }

    @PatchMapping("/{id}")
    public ActivityDto patchUpdate(@PathVariable UUID id, @RequestBody ActivityDto activityDto) throws JsonProcessingException {
        return activityService.patchUpdate(id, activityDto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return activityService.delete(id);
    }
}
