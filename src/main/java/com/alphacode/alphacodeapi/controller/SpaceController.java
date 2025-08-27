package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.SpaceDto;
import com.alphacode.alphacodeapi.service.SpaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/spaces")
@RequiredArgsConstructor
@Tag(name = "Spaces")
public class SpaceController {

    private final SpaceService service;

    @GetMapping
    public PagedResult<SpaceDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public SpaceDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public SpaceDto create(@RequestBody SpaceDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SpaceDto update(@PathVariable UUID id, @RequestBody SpaceDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public SpaceDto patchUpdate(@PathVariable UUID id, @RequestBody SpaceDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
