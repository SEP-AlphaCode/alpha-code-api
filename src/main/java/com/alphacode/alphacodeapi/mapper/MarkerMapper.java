package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.MarkerDto;
import com.alphacode.alphacodeapi.entity.Marker;

public class MarkerMapper {
    public static MarkerDto toDto(Marker marker) {
        if (marker == null) return null;

        MarkerDto dto = new MarkerDto();
        dto.setId(marker.getId());
        dto.setName(marker.getName());
        dto.setStatus(marker.getStatus());
        dto.setCreatedDate(marker.getCreatedDate());
        dto.setLastEdited(marker.getLastEdited());
        dto.setActivityId(marker.getActivityId());
        if (marker.getActivity() != null) {
            dto.setActivityName(marker.getActivity().getName());
        }

        return dto;
    }

    public static Marker toEntity(MarkerDto dto) {
        if (dto == null) return null;

        Marker marker = new Marker();
        marker.setId(dto.getId());
        marker.setName(dto.getName());
        marker.setStatus(dto.getStatus());
        marker.setCreatedDate(dto.getCreatedDate());
        marker.setLastEdited(dto.getLastEdited());
        marker.setActivityId(dto.getActivityId());

        return marker;
    }
}
