package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.TeacherClassDto;
import com.alphacode.alphacodeapi.entity.TeacherClass;

public class TeacherClassMapper {
public static TeacherClassDto toDto(TeacherClass entity) {
    if (entity == null) return null;

    return TeacherClassDto.builder()
            .teacherId(entity.getTeacherId())
            .teacherName(entity.getTeacher() != null ? entity.getTeacher().getFullName() : null)
            .classId(entity.getClassId())
            .className(entity.getClassEntity() != null ? entity.getClassEntity().getName() : null)
            .status(entity.getStatus())
            .createdDate(entity.getCreatedDate())
            .lastUpdate(entity.getLastUpdate())
            .build();
}

public static TeacherClass toEntity(TeacherClassDto dto) {
    if (dto == null) return null;

    return TeacherClass.builder()
            .teacherId(dto.getTeacherId())
            .classId(dto.getClassId())
            .status(dto.getStatus())
            .build();
}
}