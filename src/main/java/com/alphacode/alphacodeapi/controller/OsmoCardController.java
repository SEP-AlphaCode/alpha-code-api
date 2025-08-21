package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.service.OsmoCardService;
import com.alphacode.alphacodeapi.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/osmo-cards")
@RequiredArgsConstructor
@Tag(name = "OsmoCards")
public class OsmoCardController {
    private final OsmoCardService service;

    @GetMapping
    public PagedResult<OsmoCardDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public OsmoCardDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    public OsmoCardDto create(@RequestBody OsmoCardDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OsmoCardDto update(@PathVariable UUID id, @RequestBody OsmoCardDto dto){
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public OsmoCardDto patchUpdate(@PathVariable UUID id, @RequestBody OsmoCardDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
