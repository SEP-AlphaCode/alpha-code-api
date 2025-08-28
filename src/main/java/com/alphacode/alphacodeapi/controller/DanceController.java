package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.DanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dances")
@RequiredArgsConstructor
@Tag(name = "Dances")
public class DanceController {

    private final DanceService service;

    @GetMapping
    @Operation(summary = "Get all dances with pagination and optional status filter")
    public PagedResult<DanceDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dance by id")
    public DanceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new dance")
    public DanceDto create(@RequestBody DanceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update dance by id")
    public DanceDto update(@PathVariable UUID id, @RequestBody DanceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update dance by id")
    public DanceDto patchUpdate(@PathVariable UUID id, @RequestBody DanceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete dance by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}