package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.MarkerDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.MarkerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/markers")
@RequiredArgsConstructor
@Tag(name = "Markers")
public class MarkerController {

    private final MarkerService service;

    @GetMapping
    public PagedResult<MarkerDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public MarkerDto getById(@PathVariable String id) {
        return service.getById(java.util.UUID.fromString(id));
    }

    @PostMapping()
    public MarkerDto create(@RequestBody MarkerDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public MarkerDto update(@PathVariable String id, @RequestBody MarkerDto dto){
        return service.update(java.util.UUID.fromString(id), dto);
    }

    @PatchMapping("/{id}")
    public MarkerDto patchUpdate(@PathVariable String id, @RequestBody MarkerDto dto) {
        return service.patchUpdate(java.util.UUID.fromString(id), dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }

}
