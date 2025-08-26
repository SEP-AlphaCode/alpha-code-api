package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.entity.Activity;
import com.alphacode.alphacodeapi.repository.MusicRepository;
import com.alphacode.alphacodeapi.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Activity entity and ActivityDto.
 */
@Component
@RequiredArgsConstructor
public class ActivityMapper {

    private final OrganizationRepository organizationRepository;
    private final MusicRepository musicRepository;

    /**
     * Converts an Activity entity to ActivityDto.
     *
     * @param activity the Activity entity to convert
     * @return the converted ActivityDto, or null if the input is null
     */
    public ActivityDto toDto(Activity activity) {
        if (activity == null) {
            return null;
        }

        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setType(activity.getType());
        dto.setData(activity.getData());
        dto.setStatus(activity.getStatus());
        dto.setCreateDate(activity.getCreateDate());
        dto.setLastUpdate(activity.getLastUpdate());
        dto.setOrganizationId(activity.getOrganizationId());
        dto.setDescription(activity.getDescription());
        dto.setImageUrl(activity.getImageUrl());
        dto.setMusicId(activity.getMusicId());

        // Lấy organizationName từ organizationId
        if (activity.getOrganizationId() != null) {
            dto.setOrganizationName(
                    organizationRepository.findById(activity.getOrganizationId())
                            .map(org -> org.getName())
                            .orElse(null)
            );
        }

        // Lấy musicName từ musicId
        if (activity.getMusicId() != null) {
            dto.setMusicName(
                    musicRepository.findById(activity.getMusicId())
                            .map(music -> music.getName())
                            .orElse(null)
            );
        }

        return dto;
    }

    public Activity toEntity(ActivityDto dto) {
        if (dto == null) {
            return null;
        }

        return Activity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .data(dto.getData())
                .status(dto.getStatus())
                .createDate(dto.getCreateDate() != null ? dto.getCreateDate() : null)
                .lastUpdate(dto.getLastUpdate())
                .organizationId(dto.getOrganizationId())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .musicId(dto.getMusicId())
                .build();
    }

    public Activity updateEntity(ActivityDto dto, Activity entity) {
        if (dto == null || entity == null) {
            return entity;
        }

        if (dto.getName() != null && !dto.getName().equals(entity.getName())) {
            entity.setName(dto.getName());
        }
        if (dto.getType() != null && !dto.getType().equals(entity.getType())) {
            entity.setType(dto.getType());
        }
        if (dto.getData() != null && !dto.getData().equals(entity.getData())) {
            entity.setData(dto.getData());
        }
        if (dto.getStatus() != null && !dto.getStatus().equals(entity.getStatus())) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getDescription() != null && !dto.getDescription().equals(entity.getDescription())) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getImageUrl() != null && !dto.getImageUrl().equals(entity.getImageUrl())) {
            entity.setImageUrl(dto.getImageUrl());
        }
        if (dto.getOrganizationId() != null && !dto.getOrganizationId().equals(entity.getOrganizationId())) {
            entity.setOrganizationId(dto.getOrganizationId());
        }
        if (dto.getMusicId() != null && !dto.getMusicId().equals(entity.getMusicId())) {
            entity.setMusicId(dto.getMusicId());
        }

        // update timestamp
        entity.setLastUpdate(java.time.LocalDateTime.now());

        return entity;
    }
}
