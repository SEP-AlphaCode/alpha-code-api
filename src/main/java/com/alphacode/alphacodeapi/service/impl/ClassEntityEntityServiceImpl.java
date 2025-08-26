package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ClassEntityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.ClassEntity;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ClassEntityMapper;
import com.alphacode.alphacodeapi.repository.ClassEntityRepository;
import com.alphacode.alphacodeapi.service.ClassEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassEntityEntityServiceImpl implements ClassEntityService {

    private final ClassEntityRepository repository;

    @Override
    public PagedResult<ClassEntityDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ClassEntity> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(ClassEntityMapper::toDto));
    }

    @Override
    public ClassEntityDto getById(UUID id) {
        ClassEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        return ClassEntityMapper.toDto(entity);
    }

    @Override
    public ClassEntityDto create(ClassEntityDto dto) {
        ClassEntity entity = ClassEntityMapper.toEntity(dto);
        entity.setCreateDate(LocalDateTime.now());
        entity.setStatus(1);
        ClassEntity saved = repository.save(entity);
        return ClassEntityMapper.toDto(saved);
    }

    @Override
    public ClassEntityDto update(UUID id, ClassEntityDto dto) {
        ClassEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        existing.setName(dto.getName());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setStatus(dto.getStatus());

        ClassEntity updated = repository.save(existing);
        return ClassEntityMapper.toDto(updated);
    }

    @Override
    public ClassEntityDto patchUpdate(UUID id, ClassEntityDto dto) {
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
        return ClassEntityMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        ClassEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        entity.setStatus(0); // 👉 soft delete
        entity.setLastUpdate(LocalDateTime.now());
        repository.save(entity);
        return "Xoá thành công Class với ID: " + id;
    }
}
