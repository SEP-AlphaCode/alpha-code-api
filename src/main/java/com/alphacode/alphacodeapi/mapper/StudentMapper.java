package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.StudentDto;
import com.alphacode.alphacodeapi.entity.Student;

public class StudentMapper {
    public static StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setClassId(student.getAClass() != null ? student.getAClass().getId() : null);
        dto.setFacialRecordData(student.getFacialRecordData());
        dto.setShortName(student.getShortName());
        dto.setNickname(student.getNickname());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setGender(student.getGender());
        dto.setStatus(student.getStatus());
        dto.setLastUpdate(student.getLastUpdate());
        dto.setCreateDate(student.getCreateDate());
        dto.setImage(student.getImage());

        if (student.getAClass() != null) {
            dto.setClassName(student.getAClass().getName());
        }

        return dto;
    }

    public static Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }
        Student student = new Student();
        student.setId(dto.getId());
        student.setFullName(dto.getFullName());
        student.setClassId(dto.getClassId());
        student.setFacialRecordData(dto.getFacialRecordData());
        student.setShortName(dto.getShortName());
        student.setNickname(dto.getNickname());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());
        student.setStatus(dto.getStatus());
        student.setLastUpdate(dto.getLastUpdate());
        student.setCreateDate(dto.getCreateDate());
        student.setImage(dto.getImage());
        // Assuming AClass is set separately
        return student;
    }
}
