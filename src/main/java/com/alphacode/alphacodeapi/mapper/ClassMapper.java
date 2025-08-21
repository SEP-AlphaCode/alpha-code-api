package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.entity.Class;

public class ClassMapper {
    public static ClassDto toDto(Class class1) {
        if (class1 == null) {
            return null;
        }
        ClassDto dto = new ClassDto();
        dto.setId(class1.getId());
        dto.setName(class1.getName());
        dto.setCreatedDate(class1.getCreateDate());
        dto.setLastUpdate(class1.getLastUpdate());
        dto.setStatus(class1.getStatus());

//        if(class1.getTeacherClasses() != null) {
//            dto.setTeacherNa
//        }
        return dto;
    }
}
