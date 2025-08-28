package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ClassEntityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ClassEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
@Tag(name = "Classes")
public class ClassController {
    private final ClassEntityService service;

    @GetMapping
    @Operation(summary = "Get all classes with pagination and optional status filter")
    public PagedResult<ClassEntityDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get class by id")
    public ClassEntityDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new class")
    public ClassEntityDto create(@RequestBody ClassEntityDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update class by id")
    public ClassEntityDto update(@PathVariable UUID id, @RequestBody ClassEntityDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update class by id")
    public ClassEntityDto patchUpdate(@PathVariable UUID id, @RequestBody ClassEntityDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete class by id")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
