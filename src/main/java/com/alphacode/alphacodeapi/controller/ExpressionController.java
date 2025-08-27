package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ExpressionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ExpressionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expressions")
@RequiredArgsConstructor
@Tag(name = "Expressions")
public class ExpressionController {

    private final ExpressionService service;

    @GetMapping
    public PagedResult<ExpressionDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public ExpressionDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public ExpressionDto create(@RequestBody ExpressionDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ExpressionDto update(@PathVariable UUID id, @RequestBody ExpressionDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ExpressionDto patchUpdate(@PathVariable UUID id, @RequestBody ExpressionDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
