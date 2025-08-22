package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDetailDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.ActivityDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activity-details")
@RequiredArgsConstructor
@Tag(name = "ActivityDetails")
public class ActivityDetailController {

    private final ActivityDetailService service;

    @GetMapping
    public PagedResult<ActivityDetailDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "type", required = false) String type
    ) {
        return service.getAll(page, size, type);
    }

    @GetMapping("/{id}")
    public ActivityDetailDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public ActivityDetailDto create(@RequestBody ActivityDetailDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ActivityDetailDto update(@PathVariable Integer id, @RequestBody ActivityDetailDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ActivityDetailDto patchUpdate(@PathVariable Integer id, @RequestBody ActivityDetailDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
