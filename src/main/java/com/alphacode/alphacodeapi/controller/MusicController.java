package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/musics")
@RequiredArgsConstructor
@Tag(name = "Musics", description = "APIs for managing music")
public class MusicController {

    private final MusicService service;

    @GetMapping
    @Operation(summary = "Get all musics with pagination")
    public PagedResult<MusicDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) { // sửa Boolean → Integer
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get music by ID")
    public MusicDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Create new music")
    public MusicDto create(@RequestBody MusicDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update music")
    public MusicDto update(@PathVariable UUID id, @RequestBody MusicDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update music")
    public MusicDto patchUpdate(@PathVariable UUID id, @RequestBody MusicDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete music (soft delete)")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
