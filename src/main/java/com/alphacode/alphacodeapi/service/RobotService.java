package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotDto;

import java.util.UUID;

public interface RobotService {
    PagedResult<RobotDto> getAll(int page, int size, UUID organizationId, Integer status);

    RobotDto getById(UUID id);

    RobotDto create(RobotDto dto);

    RobotDto update(UUID id, RobotDto dto);

    RobotDto patchUpdate(UUID id, RobotDto dto);

    String delete(UUID id);

    RobotDto changeStatus(UUID id, Integer status);
}
