package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityDetailDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.ActivityDetail;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActivityDetailMapper;
import com.alphacode.alphacodeapi.repository.ActivityDetailRepository;
import com.alphacode.alphacodeapi.service.ActivityDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivityDetailServiceImpl implements ActivityDetailService {

    private final ActivityDetailRepository repository;

    @Override
    public PagedResult<ActivityDetailDto> getAll(int page, int size, String type) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ActivityDetail> pageResult;

        if (type != null && !type.isEmpty()) {
            pageResult = repository.findAllByTypeContainingIgnoreCase(type, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(ActivityDetailMapper::toDto));
    }

    @Override
    public ActivityDetailDto getById(Integer id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityDetail not found"));
        return ActivityDetailMapper.toDto(entity);
    }

    @Override
    public ActivityDetailDto create(ActivityDetailDto dto) {
        var entity = ActivityDetailMapper.toEntity(dto);
        entity.setId(null);
        entity.setDuration(dto.getDuration());
        entity.setStartTime(dto.getStartTime());
        var saved = repository.save(entity);
        return ActivityDetailMapper.toDto(saved);
    }

    @Override
    public ActivityDetailDto update(Integer id, ActivityDetailDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityDetail not found"));

        existing.setType(dto.getType());
        existing.setStartTime(dto.getStartTime());
        existing.setDuration(dto.getDuration());
        existing.setExpressionId(dto.getExpressionId());
        existing.setActionId(dto.getActionId());
        existing.setDanceId(dto.getDanceId());
        existing.setActivityId(dto.getActivityId());
        existing.setRgbId(dto.getRgbId());
//        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return ActivityDetailMapper.toDto(updated);
    }

    @Override
    public ActivityDetailDto patchUpdate(Integer id, ActivityDetailDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityDetail not found"));

        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getStartTime() != null) existing.setStartTime(dto.getStartTime());
        if (dto.getDuration() != null) existing.setDuration(dto.getDuration());
        if (dto.getExpressionId() != null) existing.setExpressionId(dto.getExpressionId());
        if (dto.getActionId() != null) existing.setActionId(dto.getActionId());
        if (dto.getDanceId() != null) existing.setDanceId(dto.getDanceId());
        if (dto.getActivityId() != null) existing.setActivityId(dto.getActivityId());
        if (dto.getRgbId() != null) existing.setRgbId(dto.getRgbId());
//        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return ActivityDetailMapper.toDto(updated);
    }

    @Override
    public String delete(Integer id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityDetail not found"));

        repository.delete(existing);
        return "Deleted ActivityDetail with ID: " + id;
    }
}