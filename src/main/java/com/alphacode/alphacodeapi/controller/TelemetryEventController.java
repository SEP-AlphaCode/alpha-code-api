package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TelemetryEventDto;
import com.alphacode.alphacodeapi.service.TelemetryEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telemetry-events")
@RequiredArgsConstructor
@Tag(name = "Telemetry Events")
public class TelemetryEventController {

    private final TelemetryEventService service;

    @GetMapping
    @Operation(summary = "Get all telemetry events with pagination")
    public PagedResult<TelemetryEventDto> getAll(
            @RequestParam(value = "robotId", required = false) UUID robotId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
            ) {
        return service.getAll(robotId, page, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get telemetry event by id")
    public TelemetryEventDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Create new telemetry event")
    public TelemetryEventDto create(@RequestBody TelemetryEventDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Update telemetry event by id")
    public TelemetryEventDto update(@PathVariable UUID id, @RequestBody TelemetryEventDto dto){
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Patch update telemetry event by id")
    public TelemetryEventDto patchUpdate(@PathVariable UUID id, @RequestBody TelemetryEventDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @Operation(summary = "Delete telemetry event by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }


}
