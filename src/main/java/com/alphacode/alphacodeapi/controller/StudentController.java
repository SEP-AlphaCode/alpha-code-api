package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.StudentDto;
import com.alphacode.alphacodeapi.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Students")
public class StudentController {

    private final StudentService service;
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @GetMapping
    public PagedResult<StudentDto> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") Integer status,
            @RequestParam(value = "fullName", required = false, defaultValue = "") String fullName,
            @RequestParam(value = "classId", required = false, defaultValue = "") UUID classId
    ) {
        return service.getAll(page, size, status, fullName, classId);
    }

    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDto create(@RequestParam("fullName") String fullName,
                             @RequestParam("classId") UUID classId,
                             @RequestParam("shortName") String shortName,
                             @RequestParam("nickname") String nickname,
                             @RequestParam(value = "dateOfBirth", defaultValue = "1900-01-01T00:00:00") String dateOfBirth,
                             @RequestParam("gender") Integer gender,
                             @RequestParam("status") Integer status,
                             @RequestPart(value = "image", required = true) MultipartFile image) {

        StudentDto dto = new StudentDto();
        dto.setFullName(fullName);
        dto.setClassId(classId);
        dto.setShortName(shortName);
        dto.setNickname(nickname);

        LocalDateTime dob = (dateOfBirth != null) ? LocalDateTime.parse(dateOfBirth) : LocalDateTime.now();
        dto.setDateOfBirth(dob);

        dto.setGender(gender);
        dto.setStatus(status);
        return service.create(dto, image);
    }


    @PutMapping("/{id}")
    public StudentDto update(@PathVariable UUID id, @RequestBody StudentDto dto){
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public StudentDto patchUpdate(@PathVariable UUID id, @RequestBody StudentDto dto) {
        return service.patchUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
