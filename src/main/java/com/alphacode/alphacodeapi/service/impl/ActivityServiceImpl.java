package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Activity;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActivityMapper;
import com.alphacode.alphacodeapi.repository.ActivityRepository;
import com.alphacode.alphacodeapi.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;
    private final ActivityMapper mapper;

    private static final String ACTIVITY_NOT_FOUND = "Activity not found";

    @Override
    public PagedResult<ActivityDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Activity> pageResult = (status != null) ?
                repository.findAllByStatus(status, pageable) :
                repository.findAll(pageable);

        return new PagedResult<>(pageResult.map(mapper::toDto));
    }

    @Override
    public ActivityDto getById(UUID id) {
        Activity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public ActivityDto create(ActivityDto dto) {
        Activity entity = mapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());
        if (entity.getStatus() == null) entity.setStatus(1);
        Activity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public ActivityDto update(UUID id, ActivityDto dto) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing = mapper.updateEntity(dto, existing);
        existing.setLastUpdate(LocalDateTime.now());

        Activity updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public ActivityDto patchUpdate(UUID id, ActivityDto dto) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing = mapper.updateEntity(dto, existing);
        existing.setLastUpdate(LocalDateTime.now());

        Activity updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND));

        existing.setStatus(0);
        existing.setLastUpdate(LocalDateTime.now());
        repository.save(existing);
        return "Deleted successfully";
    }
}
