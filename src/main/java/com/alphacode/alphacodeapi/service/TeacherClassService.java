package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TeacherClassDto;

import java.util.UUID;

public interface TeacherClassService {
    PagedResult<TeacherClassDto> getAll(int page, int size, Integer status);

    TeacherClassDto getById(UUID id);

    TeacherClassDto create(TeacherClassDto dto);

    TeacherClassDto update(UUID id, TeacherClassDto dto);

    TeacherClassDto patchUpdate(UUID id, TeacherClassDto dto);

    String delete(UUID id);
}
