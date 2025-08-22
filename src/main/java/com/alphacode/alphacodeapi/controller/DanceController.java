package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.DanceService;
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
    public PagedResult<DanceDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public DanceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public DanceDto create(@RequestBody DanceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public DanceDto update(@PathVariable UUID id, @RequestBody DanceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public DanceDto patchUpdate(@PathVariable UUID id, @RequestBody DanceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}