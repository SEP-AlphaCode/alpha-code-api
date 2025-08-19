package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST controller for managing activities.
 */
@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
@Tag(name = "Activities", description = "APIs for managing activities")
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an activity by ID")
    public ActivityDto getActivityById(@PathVariable UUID id) {
        return activityService.getActivityById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new activity")
    public ActivityDto createActivity(@Valid @RequestBody ActivityDto activityDto) {
        return activityService.createActivity(activityDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing activity")
    public ActivityDto updateActivity(
            @PathVariable UUID id,
            @Valid @RequestBody ActivityDto activityDto) {
        return activityService.updateActivity(id, activityDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an activity by ID")
    public void deleteActivity(@PathVariable UUID id) {
        activityService.deleteActivity(id);
    }

    @GetMapping("/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get activities by organization ID with pagination")
    public Page<ActivityDto> getActivitiesByOrganizationId(
            @PathVariable UUID organizationId,
            @PageableDefault(size = 20) Pageable pageable) {
        return activityService.getActivitiesByOrganizationId(organizationId, pageable);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get activities by status with pagination")
    public Page<ActivityDto> getActivitiesByStatus(
            @PathVariable Integer status,
            @PageableDefault(size = 20) Pageable pageable) {
        return activityService.getActivitiesByStatus(status, pageable);
    }
}
