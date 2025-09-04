package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.DeviceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface DeviceService {
    PagedResult<DeviceDto> getAll(UUID spaceId, int page, int size, Integer status);

    DeviceDto getById(UUID id);

    DeviceDto create(DeviceDto dto);

    DeviceDto update(UUID id, DeviceDto dto);

    DeviceDto patchUpdate(UUID id, DeviceDto dto);

    String delete(UUID id);
}
