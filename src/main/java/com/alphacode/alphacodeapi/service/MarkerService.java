package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.MarkerDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface MarkerService {
    PagedResult<MarkerDto> getAll(int page, int size, Integer status);

    MarkerDto getById(UUID id);

    MarkerDto create(MarkerDto dto);

    MarkerDto update(UUID id, MarkerDto dto);

    MarkerDto patchUpdate(UUID id, MarkerDto dto);

    String delete(UUID id);

    MarkerDto changeMarkerStatus(UUID id, Integer status);
}
