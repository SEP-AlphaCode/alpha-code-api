package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.SpaceDto;

import java.util.UUID;

public interface SpaceService {
    PagedResult<SpaceDto> getAll(int page, int size, Integer status);

    SpaceDto getById(UUID id);

    SpaceDto create(SpaceDto dto);

    SpaceDto update(UUID id, SpaceDto dto);

    SpaceDto patchUpdate(UUID id, SpaceDto dto);

    String delete(UUID id);
}
