package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RgbDto;
import com.alphacode.alphacodeapi.dto.RoleDto;

import java.util.UUID;

public interface RgbService {
    PagedResult<RgbDto> getAll(int page, int size);

    RgbDto getById(UUID id);

    RgbDto create(RgbDto dto);

    RgbDto update(UUID id, RgbDto dto);

    RgbDto patchUpdate(UUID id, RgbDto dto);

    String delete(UUID id);
}
