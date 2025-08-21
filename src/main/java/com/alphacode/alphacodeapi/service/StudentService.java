package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;
import com.alphacode.alphacodeapi.dto.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StudentService {
    PagedResult<StudentDto> getAll(int page, int size, Integer status, String fullName, UUID classId);

    StudentDto getById(UUID id);

    StudentDto create(StudentDto dto,  MultipartFile image);

    StudentDto update(UUID id, StudentDto dto);

    StudentDto patchUpdate(UUID id, StudentDto dto);

    String delete(UUID id);
}
