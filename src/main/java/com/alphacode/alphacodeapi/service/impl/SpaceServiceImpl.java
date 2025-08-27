package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.SpaceDto;
import com.alphacode.alphacodeapi.entity.Space;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.SpaceMapper;
import com.alphacode.alphacodeapi.repository.SpaceRepository;
import com.alphacode.alphacodeapi.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository repository;

    @Override
    public PagedResult<SpaceDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Space> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(SpaceMapper::toDto));
    }

    @Override
    public SpaceDto getById(UUID id) {
        Space entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found with id " + id));
        return SpaceMapper.toDto(entity);
    }

    @Override
    public SpaceDto create(SpaceDto dto) {
        Space entity = SpaceMapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());
        Space saved = repository.save(entity);
        return SpaceMapper.toDto(saved);
    }

    @Override
    public SpaceDto update(UUID id, SpaceDto dto) {
        Space entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found with id " + id));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrganizationId(dto.getOrganizationId());
        entity.setStatus(dto.getStatus());
        entity.setLastUpdate(LocalDateTime.now());

        Space updated = repository.save(entity);
        return SpaceMapper.toDto(updated);
    }

    @Override
    public SpaceDto patchUpdate(UUID id, SpaceDto dto) {
        Space entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found with id " + id));

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getOrganizationId() != null) entity.setOrganizationId(dto.getOrganizationId());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

        entity.setLastUpdate(LocalDateTime.now());

        Space updated = repository.save(entity);
        return SpaceMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        Space entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Space not found with id " + id));
        repository.delete(entity);
        return "Deleted successfully";
    }
}
