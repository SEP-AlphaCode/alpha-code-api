package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.entity.Class;

public class ClassMapper {

    public static ClassDto toDto(Class classEntity) {
        if (classEntity == null) {
            return null;
        }
        return new ClassDto(
                classEntity.getId(),
                classEntity.getName(),
                classEntity.getCreateDate(),
                classEntity.getLastUpdate(),
                classEntity.getStatus()
        );
    }

    public static Class toEntity(ClassDto dto) {
        if (dto == null) {
            return null;
        }
        return Class.builder()
                .id(dto.getId())
                .name(dto.getName())
                .createDate(dto.getCreateDate())
                .lastUpdate(dto.getLastUpdate())
                .status(dto.getStatus())
                .build();
    }
}
