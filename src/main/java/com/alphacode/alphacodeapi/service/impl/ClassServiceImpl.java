package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.ClassEntity;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ClassMapper;
import com.alphacode.alphacodeapi.repository.ClassRepository;
import com.alphacode.alphacodeapi.service.ClassService;
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
public class ClassServiceImpl implements ClassService {

    private final ClassRepository repository;

    @Override
    @Cacheable(value = "classes_list", key = "{#page, #size, #status}")
    public PagedResult<ClassDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ClassEntity> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(ClassMapper::toDto));
    }

    @Override
    @Cacheable(value = "classes", key = "#id")
    public ClassDto getById(UUID id) {
        ClassEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        return ClassMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"classes_list", "classes"}, allEntries = true)
    public ClassDto create(ClassDto dto) {
        ClassEntity entity = ClassMapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(1);
        ClassEntity saved = repository.save(entity);
        return ClassMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"classes_list"}, allEntries = true)
    @CachePut(value = "classes", key = "#id")
    public ClassDto update(UUID id, ClassDto dto) {
        ClassEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        existing.setName(dto.getName());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setStatus(dto.getStatus());

        ClassEntity updated = repository.save(existing);
        return ClassMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"classes_list"}, allEntries = true)
    @CachePut(value = "classes", key = "#id")
    public ClassDto patchUpdate(UUID id, ClassDto dto) {
        ClassEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        existing.setLastUpdate(LocalDateTime.now());

        ClassEntity updated = repository.save(existing);
        return ClassMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"classes_list", "classes"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        ClassEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        entity.setStatus(0); // 👉 soft delete
        entity.setLastUpdate(LocalDateTime.now());
        repository.save(entity);
        return "Xoá thành công Class với ID: " + id;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"classes_list"}, allEntries = true)
    @CachePut(value = "classes", key = "#id")
    public ClassDto changeClassStatus(UUID id, Integer status) {
        ClassEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        existing.setStatus(status);
        existing.setLastUpdate(LocalDateTime.now());
        ClassEntity updated = repository.save(existing);
        return ClassMapper.toDto(updated);
    }
}
