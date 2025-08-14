package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.entity.Action;

public class ActionMapper {

    public static ActionDto toDto(Action action) {
        if (action == null) {
            return null;
        }
        return ActionDto.builder()
                .id(action.getId())
                .name(action.getName())
                .description(action.getDescription())
                .duration(action.getDuration())
                .status(action.getStatus())
                .createdDate(action.getCreateDate())
                .lastUpdate(action.getLastUpdate())
                .canInterrupt(action.getCanInterrupt())
                .build();
    }

    public static Action toEntity(ActionDto dto) {
        if (dto == null) {
            return null;
        }
        return Action.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .status(dto.getStatus())
                .createDate(dto.getCreatedDate())
                .lastUpdate(dto.getLastUpdate())
                .canInterrupt(dto.getCanInterrupt())
                .build();
    }

    public static void updateEntity(ActionDto dto, Action entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDuration(dto.getDuration());
        entity.setStatus(dto.getStatus());
        entity.setCanInterrupt(dto.getCanInterrupt());
    }
}
