package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActionDto;

import java.util.List;
import java.util.UUID;

public interface ActionService {
    List<ActionDto> getAllActions();
    ActionDto getActionById(UUID id);
    ActionDto createAction(ActionDto actionDto);
    ActionDto updateAction(UUID id, ActionDto actionDto);
    void deleteAction(UUID id);
}
