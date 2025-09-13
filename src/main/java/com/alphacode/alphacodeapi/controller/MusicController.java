package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.service.MusicService;
import com.alphacode.alphacodeapi.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/musics")
@RequiredArgsConstructor
@Tag(name = "Musics")
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new music", description = "Upload music file và image kèm thông tin music")
    public MusicDto create(
            @RequestParam("name") String name,

            @RequestParam("duration") Double duration,

            @RequestParam("status") Integer status,

            @RequestParam("classId") UUID classId,

            @RequestPart("musicFile") MultipartFile musicFile,

            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        MusicDto musicDto = new MusicDto();
        musicDto.setName(name);
        musicDto.setDuration(duration);
        musicDto.setStatus(status);
        musicDto.setClassId(classId);

        return service.create(musicDto, musicFile, imageFile);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update music")
    public MusicDto patchUpdate(@PathVariable UUID id, @Validated(OnUpdate.class) @RequestBody MusicDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete music (soft delete)")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
