package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface DanceService {
    PagedResult<DanceDto> getPagedDances(int page, int size, String search);

    DanceDto getById(UUID id);

    DanceDto create(DanceDto dto);

    DanceDto update(UUID id, DanceDto dto);

    DanceDto patchUpdate(UUID id, DanceDto dto);

    DanceDto changeDanceStatus(UUID id, Integer status);

    String delete(UUID id);
}
