package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TelemetryEventDto;

import java.util.UUID;

public interface TelemetryEventService {
    PagedResult<TelemetryEventDto> getAll(UUID robotId, int page, int size);

    TelemetryEventDto getById(UUID id);

    TelemetryEventDto create(TelemetryEventDto dto);

    TelemetryEventDto update(UUID id, TelemetryEventDto dto);

    TelemetryEventDto patchUpdate(UUID id, TelemetryEventDto dto);

    String delete(UUID id);


}
