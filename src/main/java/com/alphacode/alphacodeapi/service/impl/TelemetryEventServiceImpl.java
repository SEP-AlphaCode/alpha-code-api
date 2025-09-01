package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TelemetryEventDto;
import com.alphacode.alphacodeapi.entity.Role;
import com.alphacode.alphacodeapi.entity.TelemetryEvent;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RoleMapper;
import com.alphacode.alphacodeapi.mapper.TelemetryEventMapper;
import com.alphacode.alphacodeapi.repository.TelemetryEventRepository;
import com.alphacode.alphacodeapi.service.TelemetryEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TelemetryEventServiceImpl implements TelemetryEventService {

    private final TelemetryEventRepository repository;

    @Override
    public PagedResult<TelemetryEventDto> getAll(UUID robotId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TelemetryEvent> pageResult;

        if (robotId != null) {
            pageResult = repository.findAllByRobotId(robotId, pageable);
        } else {

            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(TelemetryEventMapper::toDto));
    }

    @Override
    public TelemetryEventDto getById(UUID id) {
        var telemetryEvent = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telemetry Event not found"));
        return TelemetryEventMapper.toDto(telemetryEvent);
    }

    @Override
    @Transactional
    public TelemetryEventDto create(TelemetryEventDto dto) {

        var telemetryEvent = TelemetryEventMapper.toEntity(dto);
        telemetryEvent.setCreatedDate(LocalDateTime.now());

        var saved = repository.save(telemetryEvent);
        return TelemetryEventMapper.toDto(saved);
    }

    @Override
    @Transactional
    public TelemetryEventDto update(UUID id, TelemetryEventDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telemetry Event not found"));

        existing.setRobotId(dto.getRobotId());
        existing.setActivityId(dto.getActivityId());
        existing.setEventType(dto.getEventType());
        existing.setLatency(dto.getLatency());

        var updated = repository.save(existing);
        return TelemetryEventMapper.toDto(updated);
    }

    @Override
    @Transactional
    public TelemetryEventDto patchUpdate(UUID id, TelemetryEventDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telemetry Event not found"));

        if (dto.getRobotId() != null) {
            existing.setRobotId(dto.getRobotId());
        }
        if (dto.getActivityId() != null) {
            existing.setActivityId(dto.getActivityId());
        }
        if (dto.getEventType() != null) {
            existing.setEventType(dto.getEventType());
        }
        if (dto.getLatency() != null) {
            existing.setLatency(dto.getLatency());
        }

        var updated = repository.save(existing);
        return TelemetryEventMapper.toDto(updated);
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        try {
            var telemetryEvent = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Telemetry Event not found"));

            repository.delete(telemetryEvent);

            return "Telemetry Event deleted successfully with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Telemetry Event", e);
        }
    }
}
