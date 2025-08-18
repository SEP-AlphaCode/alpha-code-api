package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.AccountDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleDto> getAll();

    RoleDto getById(UUID id);

    RoleDto create(RoleDto roleDto);

    RoleDto update(UUID id,RoleDto roleDto);

    void delete(UUID id);
}
