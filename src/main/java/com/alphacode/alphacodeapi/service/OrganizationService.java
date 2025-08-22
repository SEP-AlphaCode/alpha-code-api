package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface OrganizationService {
    PagedResult<OrganizationDto> getAll(int page, int size, Integer status);

    OrganizationDto getById(UUID id);

    OrganizationDto create(OrganizationDto dto);

    OrganizationDto update(UUID id, OrganizationDto dto);

    OrganizationDto patchUpdate(UUID id, OrganizationDto dto);

    String delete(UUID id);
}
