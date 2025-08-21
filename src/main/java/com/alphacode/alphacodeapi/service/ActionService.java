package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActionDto;

import java.util.List;
import java.util.UUID;

import com.alphacode.alphacodeapi.dto.PagedResult;

public interface ActionService {
    PagedResult<ActionDto> getAllActions(int page, int size, Integer status);
    ActionDto getActionById(UUID id);
    ActionDto createAction(ActionDto actionDto);
    ActionDto updateAction(UUID id, ActionDto actionDto);
    void deleteAction(UUID id);
}
