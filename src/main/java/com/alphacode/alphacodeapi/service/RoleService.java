package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;

import java.util.UUID;

public interface RoleService {
    PagedResult<RoleDto> getAll(int page, int size, Integer status);

    RoleDto getById(UUID id);

    RoleDto create(RoleDto roleDto);

    RoleDto update(UUID id, RoleDto roleDto);

    RoleDto patchUpdate(UUID id, RoleDto roleDto);

    String delete(UUID id);
}
