package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ExpressionDto;
import com.alphacode.alphacodeapi.entity.Expression;

public class ExpressionMapper {
    public static ExpressionDto toDto(Expression entity) {
        if (entity == null) return null;

        ExpressionDto dto = new ExpressionDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImageUrl(entity.getImageUrl());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdate(entity.getLastUpdate());

        return dto;
    }

    public static Expression toEntity(ExpressionDto dto) {
        if (dto == null) return null;

        Expression entity = new Expression();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImageUrl(dto.getImageUrl());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastUpdate(dto.getLastUpdate());

        return entity;
    }
}