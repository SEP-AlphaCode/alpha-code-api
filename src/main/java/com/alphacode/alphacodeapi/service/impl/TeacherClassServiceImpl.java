package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.TeacherClassDto;
import com.alphacode.alphacodeapi.entity.TeacherClass;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.TeacherClassMapper;
import com.alphacode.alphacodeapi.repository.TeacherClassRepository;
import com.alphacode.alphacodeapi.service.TeacherClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherClassServiceImpl implements TeacherClassService {

    private final TeacherClassRepository repository;

    @Override
    @Cacheable(value = "teacher_class_list", key = "{#page, #size, #status}")
    public PagedResult<TeacherClassDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TeacherClass> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(TeacherClassMapper::toDto));
    }

    @Override
    @Cacheable(value = "teacher_class", key = "#id")
    public TeacherClassDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherClass not found"));
        return TeacherClassMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"teacher_class_list", "teacher_class"}, allEntries = true)
    public TeacherClassDto create(TeacherClassDto dto) {
        var entity = TeacherClassMapper.toEntity(dto);

        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());

        var saved = repository.save(entity);
        return TeacherClassMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"teacher_class_list"}, allEntries = true)
    @CachePut(value = "teacher_class", key = "#id")
    public TeacherClassDto update(UUID id, TeacherClassDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherClass not found"));

        existing.setTeacherId(dto.getTeacherId());
        existing.setClassId(dto.getClassId());
        existing.setStatus(dto.getStatus());
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return TeacherClassMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"teacher_class_list"}, allEntries = true)
    @CachePut(value = "teacher_class", key = "#id")
    public TeacherClassDto patchUpdate(UUID id, TeacherClassDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherClass not found"));

        if (dto.getTeacherId() != null) existing.setTeacherId(dto.getTeacherId());
        if (dto.getClassId() != null) existing.setClassId(dto.getClassId());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());

        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return TeacherClassMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"teacher_class_list"}, allEntries = true)
    @CachePut(value = "teacher_class", key = "#id")
    public String delete(UUID id) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherClass not found"));

        existing.setStatus(0); // Soft delete
        existing.setLastUpdate(LocalDateTime.now());
        repository.save(existing);
        return "Deleted TeacherClass with ID: " + id;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"teacher_class_list"}, allEntries = true)
    @CachePut(value = "teacher_class", key = "#id")
    public TeacherClassDto changeStatus(UUID id, Integer status) {
        TeacherClass entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherClass not found"));

        entity.setStatus(status);
        entity.setLastUpdate(LocalDateTime.now());

        TeacherClass updated = repository.save(entity);
        return TeacherClassMapper.toDto(updated);
    }
}
