package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Dance;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.DanceMapper;
import com.alphacode.alphacodeapi.repository.DanceRepository;
import com.alphacode.alphacodeapi.service.DanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DanceServiceImpl implements DanceService {

    private final DanceRepository repository;

    @Override
    public PagedResult<DanceDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Dance> pageResult;
        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }
        return new PagedResult<>(pageResult.map(DanceMapper::toDto));
    }

    @Override
    public DanceDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dance not found"));
        return DanceMapper.toDto(entity);
    }

    @Override
    public DanceDto create(DanceDto dto) {
        var entity = DanceMapper.toEntity(dto);
        entity.setId(null);
        entity.setLastUpdate(LocalDateTime.now());
        entity.setCreatedDate(LocalDateTime.now());
        var saved = repository.save(entity);
        return DanceMapper.toDto(saved);
    }

    @Override
    public DanceDto update(UUID id, DanceDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dance not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setStatus(dto.getStatus());
        existing.setDuration(dto.getDuration());
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return DanceMapper.toDto(updated);
    }

    @Override
    public DanceDto patchUpdate(UUID id, DanceDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dance not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDuration() != null) existing.setDuration(dto.getDuration());

        existing.setLastUpdate(LocalDateTime.now());
        var updated = repository.save(existing);
        return DanceMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dance not found"));
        existing.setStatus(0);
        repository.save(existing);
        return "Deleted Dance with ID: " + id;
    }
}