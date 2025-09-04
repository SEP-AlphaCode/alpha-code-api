package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.RobotPermissionDto;
import com.alphacode.alphacodeapi.entity.RobotPermission;

public class RobotPermissionMapper {
    public static RobotPermissionDto toDto(RobotPermission entity) {
        if (entity == null) return null;

        RobotPermissionDto dto = new RobotPermissionDto();
        dto.setId(entity.getId());
        dto.setRobotId(entity.getRobotId());
        dto.setActivityId(entity.getActivityId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdate(entity.getLastUpdate());

        if (entity.getRobot() != null) {
            dto.setRobotName(entity.getRobot().getName());
        }
        if (entity.getActivity() != null) {
            dto.setActivityName(entity.getActivity().getName());
        }

        return dto;
    }

    public static RobotPermission toEntity(RobotPermissionDto dto) {
        if (dto == null) return null;

        return RobotPermission.builder()
                .id(dto.getId())
                .robotId(dto.getRobotId())
                .activityId(dto.getActivityId())
                .status(dto.getStatus())
                .createdDate(dto.getCreatedDate())
                .lastUpdate(dto.getLastUpdate())
                .build();
    }
}
