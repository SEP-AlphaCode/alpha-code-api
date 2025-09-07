package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotPermissionDto;

import java.util.UUID;

public interface RobotPermissionService {
    PagedResult<RobotPermissionDto> getAll(int page, int size, Integer status);

    RobotPermissionDto getById(UUID id);

    RobotPermissionDto create(RobotPermissionDto dto);

    RobotPermissionDto update(UUID id, RobotPermissionDto dto);

    RobotPermissionDto patchUpdate(UUID id, RobotPermissionDto dto);

    String delete(UUID id);

    RobotPermissionDto changeStatus(UUID id, Integer status);
}
