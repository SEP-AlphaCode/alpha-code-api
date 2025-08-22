package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations")
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping
    public PagedResult<OrganizationDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public OrganizationDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public OrganizationDto create(@RequestBody OrganizationDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OrganizationDto update(@PathVariable UUID id, @RequestBody OrganizationDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public OrganizationDto patchUpdate(@PathVariable UUID id, @RequestBody OrganizationDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}