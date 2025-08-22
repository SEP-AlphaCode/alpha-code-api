package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Organization;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.OrganizationMapper;
import com.alphacode.alphacodeapi.repository.OrganizationRepository;
import com.alphacode.alphacodeapi.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    public PagedResult<OrganizationDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Organization> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(OrganizationMapper::toDto));
    }

    @Override
    public OrganizationDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        return OrganizationMapper.toDto(entity);
    }

    @Override
    public OrganizationDto create(OrganizationDto dto) {
        var entity = OrganizationMapper.toEntity(dto);
        entity.setCreateDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());

        var saved = repository.save(entity);
        return OrganizationMapper.toDto(saved);
    }

    @Override
    public OrganizationDto update(UUID id, OrganizationDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        existing.setName(dto.getName());
        existing.setLocation(dto.getLocation());
        existing.setStatus(dto.getStatus());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return OrganizationMapper.toDto(updated);
    }

    @Override
    public OrganizationDto patchUpdate(UUID id, OrganizationDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        OrganizationMapper.patchUpdate(existing, dto);
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return OrganizationMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Soft delete bằng cách set status = 0
        existing.setStatus(0);
        repository.save(existing);

        return "Deleted Organization with ID: " + id;
    }
}