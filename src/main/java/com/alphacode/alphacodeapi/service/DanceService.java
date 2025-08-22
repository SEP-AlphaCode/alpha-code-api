package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface DanceService {
    PagedResult<DanceDto> getAll(int page, int size, Integer status);

    DanceDto getById(UUID id);

    DanceDto create(DanceDto dto);

    DanceDto update(UUID id, DanceDto dto);

    DanceDto patchUpdate(UUID id, DanceDto dto);

    String delete(UUID id);
}
