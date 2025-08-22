package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.TeacherClassDto;
import com.alphacode.alphacodeapi.entity.SchoolClass;
import com.alphacode.alphacodeapi.entity.TeacherClass;

import java.util.stream.Collectors;

public class SchoolClassMapper {
    public static ClassDto toDto(SchoolClass schoolClass) {
        if (schoolClass == null) {
            return null;
        }

        ClassDto dto = new ClassDto();
        dto.setId(schoolClass.getId());
        dto.setName(schoolClass.getName());
        dto.setCreatedDate(schoolClass.getCreateDate());
        dto.setLastUpdate(schoolClass.getLastUpdate());
        dto.setStatus(schoolClass.getStatus());

        // Map teacherClasses nếu cần
        if (schoolClass.getTeacherClasses() != null) {
            dto.setTeachers(
                    schoolClass.getTeacherClasses().stream()
                            .map(SchoolClassMapper::toTeacherClassDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static SchoolClass toEntity(ClassDto dto) {
        if (dto == null) {
            return null;
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(dto.getId());
        schoolClass.setName(dto.getName());
        schoolClass.setCreateDate(dto.getCreatedDate());
        schoolClass.setLastUpdate(dto.getLastUpdate());
        schoolClass.setStatus(dto.getStatus());
        return schoolClass;
    }

    private static TeacherClassDto toTeacherClassDto(TeacherClass teacherClass) {
        if (teacherClass == null) return null;

        TeacherClassDto dto = new TeacherClassDto();
        dto.setTeacherId(teacherClass.getTeacher().getId());
        dto.setTeacherName(teacherClass.getTeacher().getFullName());
        dto.setClassId(teacherClass.getSchoolClass().getId());
        dto.setClassName(teacherClass.getSchoolClass().getName());
        dto.setStatus(teacherClass.getStatus());
        dto.setCreateDate(teacherClass.getCreateDate());
        dto.setLastUpdate(teacherClass.getLastUpdate());
        return dto;
    }
}