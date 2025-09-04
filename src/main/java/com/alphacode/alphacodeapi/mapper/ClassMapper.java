package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.TeacherClassDto;
import com.alphacode.alphacodeapi.entity.ClassEntity;
import com.alphacode.alphacodeapi.entity.TeacherClass;

import java.util.stream.Collectors;

public class ClassMapper {
    public static ClassDto toDto(ClassEntity classEntity) {
        if (classEntity == null) {
            return null;
        }

        ClassDto dto = new ClassDto();
        dto.setId(classEntity.getId());
        dto.setName(classEntity.getName());
        dto.setCreatedDate(classEntity.getCreatedDate());
        dto.setLastUpdate(classEntity.getLastUpdate());
        dto.setStatus(classEntity.getStatus());

        // Map teacherClasses nếu cần
        if (classEntity.getTeacherClasses() != null) {
            dto.setTeachers(
                    classEntity.getTeacherClasses().stream()
                            .map(ClassMapper::toTeacherClassDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static ClassEntity toEntity(ClassDto dto) {
        if (dto == null) {
            return null;
        }

        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(dto.getId());
        classEntity.setName(dto.getName());
        classEntity.setCreatedDate(dto.getCreatedDate());
        classEntity.setLastUpdate(dto.getLastUpdate());
        classEntity.setStatus(dto.getStatus());
        return classEntity;
    }

    private static TeacherClassDto toTeacherClassDto(TeacherClass teacherClass) {
        if (teacherClass == null) return null;

        TeacherClassDto dto = new TeacherClassDto();
        dto.setTeacherId(teacherClass.getTeacher().getId());
        dto.setTeacherName(teacherClass.getTeacher().getFullName());
        dto.setClassId(teacherClass.getClassEntity().getId());
        dto.setClassName(teacherClass.getClassEntity().getName());
        dto.setStatus(teacherClass.getStatus());
        dto.setCreatedDate(teacherClass.getCreatedDate());
        dto.setLastUpdate(teacherClass.getLastUpdate());
        return dto;
    }
}