package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.OrganizationDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Organization;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.OrganizationMapper;
import com.alphacode.alphacodeapi.repository.OrganizationRepository;
import com.alphacode.alphacodeapi.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    @Cacheable(value = "organizations_list", key = "{#page, #size, #status}")
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
    @Cacheable(value = "organizations", key = "#id")
    public OrganizationDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        return OrganizationMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"organizations_list", "organizations"}, allEntries = true)
    public OrganizationDto create(OrganizationDto dto) {
        var entity = OrganizationMapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());

        var saved = repository.save(entity);
        return OrganizationMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"organizations_list"}, allEntries = true)
    @CachePut(value = "organizations", key = "#id")
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
    @Transactional
    @CacheEvict(value = {"organizations_list"}, allEntries = true)
    @CachePut(value = "organizations", key = "#id")
    public OrganizationDto patchUpdate(UUID id, OrganizationDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        OrganizationMapper.patchUpdate(existing, dto);
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return OrganizationMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"organizations_list", "organizations"}, allEntries = true)
    public String delete(UUID id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Soft delete bằng cách set status = 0
        existing.setStatus(0);
        repository.save(existing);

        return "Deleted Organization with ID: " + id;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"organizations_list"}, allEntries = true)
    @CachePut(value = "organizations", key = "#id")
    public OrganizationDto changeStatus(UUID id, Integer status) {
        Organization entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        entity.setStatus(status);
        entity.setLastUpdate(LocalDateTime.now());

        Organization updated = repository.save(entity);
        return OrganizationMapper.toDto(updated);
    }
}