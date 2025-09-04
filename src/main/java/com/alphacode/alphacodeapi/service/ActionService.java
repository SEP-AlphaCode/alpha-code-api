package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ActionService {
    PagedResult<ActionDto> getAllActions(int page, int size, String search);

    ActionDto getActionById(UUID id);

    ActionDto createAction(ActionDto actionDto);

    ActionDto updateAction(UUID id, ActionDto actionDto);

    void deleteAction(UUID id);
}
