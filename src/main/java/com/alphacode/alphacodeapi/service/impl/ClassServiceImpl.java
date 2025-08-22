package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.SchoolClass;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.SchoolClassMapper;
import com.alphacode.alphacodeapi.repository.ClassRepository;
import com.alphacode.alphacodeapi.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository repository;

    @Override
    public PagedResult<ClassDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SchoolClass> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(SchoolClassMapper::toDto));
    }

    @Override
    public ClassDto getById(UUID id) {
        SchoolClass entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        return SchoolClassMapper.toDto(entity);
    }

    @Override
    public ClassDto create(ClassDto dto) {
        SchoolClass entity = SchoolClassMapper.toEntity(dto);
        entity.setCreateDate(LocalDateTime.now());
        entity.setStatus(1);
        SchoolClass saved = repository.save(entity);
        return SchoolClassMapper.toDto(saved);
    }

    @Override
    public ClassDto update(UUID id, ClassDto dto) {
        SchoolClass existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        existing.setName(dto.getName());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setStatus(dto.getStatus());

        SchoolClass updated = repository.save(existing);
        return SchoolClassMapper.toDto(updated);
    }

    @Override
    public ClassDto patchUpdate(UUID id, ClassDto dto) {
        SchoolClass existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        existing.setLastUpdate(LocalDateTime.now());

        SchoolClass updated = repository.save(existing);
        return SchoolClassMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        SchoolClass entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        entity.setStatus(0); // 👉 soft delete
        entity.setLastUpdate(LocalDateTime.now());
        repository.save(entity);
        return "Xoá thành công Class với ID: " + id;
    }
}
