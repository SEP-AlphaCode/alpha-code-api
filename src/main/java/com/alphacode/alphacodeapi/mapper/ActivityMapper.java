package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDto toDto(Activity activity) {
        if (activity == null) return null;

        return ActivityDto.withAllFields(
                activity.getId(),
                activity.getName(),
                activity.getType(),
                activity.getData(),
                activity.getStatus(),
                activity.getCreateDate(),
                activity.getLastUpdate(),
                activity.getOrganizationId(),
                activity.getDescription(),
                activity.getImageUrl(),
                activity.getMusicId(),
                activity.getOrganization() != null ? activity.getOrganization().getName() : null,
                activity.getMusic() != null ? activity.getMusic().getName() : null
        );
    }

    public Activity toEntity(ActivityDto dto) {
        if (dto == null) return null;

        return Activity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .data(dto.getData())
                .status(dto.getStatus())
                .createDate(dto.getCreateDate())
                .lastUpdate(dto.getLastUpdate())
                .organizationId(dto.getOrganizationId())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .musicId(dto.getMusicId())
                .build();
    }

    public Activity updateEntity(ActivityDto dto, Activity entity) {
        if (dto == null || entity == null) return entity;

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getType() != null) entity.setType(dto.getType());
        if (dto.getData() != null) entity.setData(dto.getData());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) entity.setImageUrl(dto.getImageUrl());
        if (dto.getOrganizationId() != null) entity.setOrganizationId(dto.getOrganizationId());
        if (dto.getMusicId() != null) entity.setMusicId(dto.getMusicId());

        entity.setLastUpdate(java.time.LocalDateTime.now());

        return entity;
    }
}
