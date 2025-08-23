package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
@Tag(name = "Classes")
public class ClassController {
    private final ClassService service;

    @GetMapping
    public PagedResult<ClassDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public ClassDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public ClassDto create(@RequestBody ClassDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ClassDto update(@PathVariable UUID id, @RequestBody ClassDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ClassDto patchUpdate(@PathVariable UUID id, @RequestBody ClassDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
