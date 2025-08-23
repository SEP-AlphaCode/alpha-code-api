package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RgbDto;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.service.RgbService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rgbs")
@RequiredArgsConstructor
@Tag(name = "Rgbs")
public class RgbController {

    private final RgbService service;

    @GetMapping
    public PagedResult<RgbDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public RgbDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    public RgbDto create(@RequestBody RgbDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RgbDto update(@PathVariable UUID id, @RequestBody RgbDto dto){
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public RgbDto patchUpdate(@PathVariable UUID id, @RequestBody RgbDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
