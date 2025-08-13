package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public List<RoleDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping()
    public RoleDto create(@RequestBody RoleDto roleDto) {
        return service.create(roleDto);
    }


    @PutMapping("/{id}")
    public RoleDto update(@PathVariable UUID id, @RequestBody RoleDto dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
