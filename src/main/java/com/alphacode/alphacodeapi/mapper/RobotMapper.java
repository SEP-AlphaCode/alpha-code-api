package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.RobotDto;
import com.alphacode.alphacodeapi.entity.Robot;

public class RobotMapper {
    public static RobotDto toDto(Robot robot) {
        if (robot == null) return null;
        RobotDto dto = new RobotDto();
        dto.setId(robot.getId());
        dto.setName(robot.getName());
        dto.setIp(robot.getIp());
        dto.setCode(robot.getCode());
        dto.setType(robot.getType());
        dto.setCreatedDate(robot.getCreatedDate());
        dto.setLastUpdate(robot.getLastUpdate());
        dto.setStatus(robot.getStatus());
        dto.setOrganizationId(robot.getOrganizationId());
        return dto;
    }

    public static Robot toEntity(RobotDto dto) {
        if (dto == null) return null;
        Robot robot = new Robot();
        robot.setId(dto.getId());
        robot.setName(dto.getName());
        robot.setIp(dto.getIp());
        robot.setCode(dto.getCode());
        robot.setType(dto.getType());
        robot.setCreatedDate(dto.getCreatedDate());
        robot.setLastUpdate(dto.getLastUpdate());
        robot.setStatus(dto.getStatus());
        robot.setOrganizationId(dto.getOrganizationId());
        return robot;
    }
}
