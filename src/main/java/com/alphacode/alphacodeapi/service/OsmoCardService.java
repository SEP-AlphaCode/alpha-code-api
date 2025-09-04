package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface OsmoCardService {
    PagedResult<OsmoCardDto> getAll(int page, int size, Integer status);

    OsmoCardDto getById(UUID id);

    OsmoCardDto create(OsmoCardDto dto);

    OsmoCardDto update(UUID id, OsmoCardDto dto);

    OsmoCardDto patchUpdate(UUID id, OsmoCardDto dto);

    String delete(UUID id);
}
