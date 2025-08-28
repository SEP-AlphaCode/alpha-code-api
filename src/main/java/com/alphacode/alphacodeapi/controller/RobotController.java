package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotDto;
import com.alphacode.alphacodeapi.service.RobotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/robots")
@RequiredArgsConstructor
@Tag(name = "Robots")
public class RobotController {
    private final RobotService service;

    @GetMapping
    public PagedResult<RobotDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "organizationId", required = false) UUID organizationId,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, organizationId, status);
    }

    @GetMapping("/{id}")
    public RobotDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    public RobotDto create(@RequestBody RobotDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RobotDto update(@PathVariable UUID id, @RequestBody RobotDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public RobotDto patchUpdate(@PathVariable UUID id, @RequestBody RobotDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
