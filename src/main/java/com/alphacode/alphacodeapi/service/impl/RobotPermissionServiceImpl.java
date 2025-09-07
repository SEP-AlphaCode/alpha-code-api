package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RobotPermissionDto;
import com.alphacode.alphacodeapi.entity.RobotPermission;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RobotPermissionMapper;
import com.alphacode.alphacodeapi.repository.RobotPermissionRepository;
import com.alphacode.alphacodeapi.service.RobotPermissionService;
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
public class RobotPermissionServiceImpl implements RobotPermissionService {

    private final RobotPermissionRepository repository;

    @Override
    @Cacheable(value = "robot_permissions_list", key = "#page + #size + #status")
    public PagedResult<RobotPermissionDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RobotPermission> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(RobotPermissionMapper::toDto));
    }

    @Override
    @Cacheable(value = "robot_permissions", key = "#id")
    public RobotPermissionDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RobotPermission not found"));
        return RobotPermissionMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"robot_permissions_list", "robot_permissions"}, allEntries = true)
    public RobotPermissionDto create(RobotPermissionDto dto) {
        var entity = RobotPermissionMapper.toEntity(dto);

        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());

        var saved = repository.save(entity);
        return RobotPermissionMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"robot_permissions_list"}, allEntries = true)
    @CachePut(value = "robot_permissions", key = "#id")
    public RobotPermissionDto update(UUID id, RobotPermissionDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RobotPermission not found"));

        existing.setRobotId(dto.getRobotId());
        existing.setActivityId(dto.getActivityId());
        existing.setStatus(dto.getStatus());
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return RobotPermissionMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"robot_permissions_list"}, allEntries = true)
    @CachePut(value = "robot_permissions", key = "#id")
    public RobotPermissionDto patchUpdate(UUID id, RobotPermissionDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RobotPermission not found"));

        if (dto.getRobotId() != null) existing.setRobotId(dto.getRobotId());
        if (dto.getActivityId() != null) existing.setActivityId(dto.getActivityId());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());

        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return RobotPermissionMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"robot_permissions_list", "robot_permissions"}, allEntries = true)
    public String delete(UUID id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RobotPermission not found"));

        existing.setStatus(0); // Soft delete
        repository.save(existing);
        return "Deleted RobotPermission with ID: " + id;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"robot_permissions_list"}, allEntries = true)
    @CachePut(value = "robot_permissions", key = "#id")
    public RobotPermissionDto changeStatus(UUID id, Integer status) {
        RobotPermission entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RobotPermission not found"));

        entity.setStatus(status);
        entity.setLastUpdate(LocalDateTime.now());

        RobotPermission updated = repository.save(entity);
        return RobotPermissionMapper.toDto(updated);
    }
}
