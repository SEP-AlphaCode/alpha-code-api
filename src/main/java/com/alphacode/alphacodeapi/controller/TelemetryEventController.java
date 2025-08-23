package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TelemetryEventDto;
import com.alphacode.alphacodeapi.service.TelemetryEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telemetry-events")
@RequiredArgsConstructor
@Tag(name = "Telemetry Events")
public class TelemetryEventController {

    private final TelemetryEventService service;

    @GetMapping
    public PagedResult<TelemetryEventDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
            ) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public TelemetryEventDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/robot/{robotId}")
    public PagedResult<TelemetryEventDto> getByRobotId(
            @PathVariable UUID robotId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.getByRobotId(robotId, page, size);
    }

    @PostMapping()
    public TelemetryEventDto create(@RequestBody TelemetryEventDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TelemetryEventDto update(@PathVariable UUID id, @RequestBody TelemetryEventDto dto){
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public TelemetryEventDto patchUpdate(@PathVariable UUID id, @RequestBody TelemetryEventDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }


}
