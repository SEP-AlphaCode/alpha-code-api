package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.SpaceDto;
import com.alphacode.alphacodeapi.entity.Space;

public class SpaceMapper {
    public static SpaceDto toDto(Space entity) {
        if (entity == null) return null;

        SpaceDto dto = new SpaceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOrganizationId(entity.getOrganizationId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setOrganizationName(entity.getOrganization() != null ? entity.getOrganization().getName() : null);

        return dto;
    }

    public static Space toEntity(SpaceDto dto) {
        if (dto == null) return null;

        Space entity = new Space();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrganizationId(dto.getOrganizationId());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastUpdate(dto.getLastUpdate());

        return entity;
    }
}
