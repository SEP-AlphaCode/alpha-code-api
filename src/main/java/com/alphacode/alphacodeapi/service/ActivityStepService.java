package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ActivityStepService {
    PagedResult<ActivityStepDto> getAll(int page, int size, UUID activityId);

    ActivityStepDto getById(UUID id);

    ActivityStepDto create(ActivityStepDto dto);

    ActivityStepDto update(UUID id, ActivityStepDto dto);

    ActivityStepDto patchUpdate(UUID id, ActivityStepDto dto);

    String delete(UUID id);
}
