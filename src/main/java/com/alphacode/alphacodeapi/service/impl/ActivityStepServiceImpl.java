package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.ActivityStep;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActivityStepMapper;
import com.alphacode.alphacodeapi.repository.ActivityStepRepository;
import com.alphacode.alphacodeapi.service.ActivityStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityStepServiceImpl implements ActivityStepService {

    private final ActivityStepRepository repository;

    @Override
    public PagedResult<ActivityStepDto> getAll(int page, int size, UUID activityId) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").ascending());
        Page<ActivityStep> pageResult = (activityId != null)
                ? repository.findAllByActivityId(activityId, pageable)
                : repository.findAll(pageable);

        return new PagedResult<>(pageResult.map(ActivityStepMapper::toDto));
    }

    @Override
    public ActivityStepDto getById(UUID id) {
        ActivityStep entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityStep not found"));
        return ActivityStepMapper.toDto(entity);
    }

    @Override
    @Transactional
    public ActivityStepDto create(ActivityStepDto dto) {
        ActivityStep entity = ActivityStepMapper.toEntity(dto);
        ActivityStep saved = repository.save(entity);
        return ActivityStepMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ActivityStepDto update(UUID id, ActivityStepDto dto) {
        ActivityStep existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityStep not found"));

        // dùng mapper.toEntity để tránh lặp code
        ActivityStep updatedEntity = ActivityStepMapper.toEntity(dto);
        updatedEntity.setId(existing.getId()); // giữ lại ID

        ActivityStep updated = repository.save(updatedEntity);
        return ActivityStepMapper.toDto(updated);
    }

    @Override
    @Transactional
    public ActivityStepDto patchUpdate(UUID id, ActivityStepDto dto) {
        ActivityStep existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityStep not found"));

        // patch: chỉ update nếu dto field != null
        if (dto.getType() != null) existing.setType(dto.getType());
        if (dto.getStartTime() != null) existing.setStartTime(dto.getStartTime());
        if (dto.getDuration() != null) existing.setDuration(dto.getDuration());
        if (dto.getExpressionId() != null) existing.setExpressionId(dto.getExpressionId());
        if (dto.getActionId() != null) existing.setActionId(dto.getActionId());
        if (dto.getDanceId() != null) existing.setDanceId(dto.getDanceId());
        if (dto.getActivityId() != null) existing.setActivityId(dto.getActivityId());
        if (dto.getRgbId() != null) existing.setRgbId(dto.getRgbId());

        ActivityStep updated = repository.save(existing);
        return ActivityStepMapper.toDto(updated);
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        ActivityStep existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActivityStep not found"));
        repository.delete(existing);
        return "Deleted ActivityStep with ID: " + id;
    }
}
