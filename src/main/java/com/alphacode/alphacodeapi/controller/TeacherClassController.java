package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TeacherClassDto;
import com.alphacode.alphacodeapi.service.TeacherClassService;
import com.alphacode.alphacodeapi.validation.OnCreate;
import com.alphacode.alphacodeapi.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teacher-classes")
@RequiredArgsConstructor
@Tag(name = "TeacherClasses")
public class TeacherClassController {
    private final TeacherClassService service;

    @GetMapping
    @Operation(summary = "Get all teacher-class assignments with pagination and optional status filter")
    public PagedResult<TeacherClassDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false) Integer status) {
        return service.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher-class assignment by id")
    public TeacherClassDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new teacher-class assignment")
    public TeacherClassDto create(@Validated(OnCreate.class) @RequestBody TeacherClassDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update teacher-class assignment by id")
    public TeacherClassDto update(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody TeacherClassDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update teacher-class assignment by id")
    public TeacherClassDto patchUpdate(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody TeacherClassDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete teacher-class assignment by id (soft delete)")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
