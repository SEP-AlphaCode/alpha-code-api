package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ExpressionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Expression;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ExpressionMapper;
import com.alphacode.alphacodeapi.repository.ExpressionRepository;
import com.alphacode.alphacodeapi.service.ExpressionService;
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
public class ExpressionServiceImpl implements ExpressionService {

    private final ExpressionRepository repository;

    @Override
    @Cacheable(value = "expressions_list", key = "{#page, #size, #status}")
    public PagedResult<ExpressionDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Expression> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(ExpressionMapper::toDto));
    }

    @Override
    @Cacheable(value = "expressions", key = "#id")
    public ExpressionDto getById(UUID id) {
        Expression entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found with id " + id));
        return ExpressionMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"expressions_list", "expressions"}, allEntries = true)
    public ExpressionDto create(ExpressionDto dto) {
        Expression entity = ExpressionMapper.toEntity(dto);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdate(LocalDateTime.now());
        Expression saved = repository.save(entity);
        return ExpressionMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"expressions_list"}, allEntries = true)
    @CachePut(value = "expressions", key = "#id")
    public ExpressionDto update(UUID id, ExpressionDto dto) {
        Expression entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found with id " + id));

        entity.setName(dto.getName());
        entity.setImageUrl(dto.getImageUrl());
        entity.setStatus(dto.getStatus());
        entity.setLastUpdate(LocalDateTime.now());

        Expression updated = repository.save(entity);
        return ExpressionMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"expressions_list"}, allEntries = true)
    @CachePut(value = "expressions", key = "#id")
    public ExpressionDto patchUpdate(UUID id, ExpressionDto dto) {
        Expression entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found with id " + id));

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getImageUrl() != null) entity.setImageUrl(dto.getImageUrl());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

        entity.setLastUpdate(LocalDateTime.now());

        Expression updated = repository.save(entity);
        return ExpressionMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"expressions", "expressions_list"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        Expression entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found with id " + id));
        repository.delete(entity);
        return "Deleted successfully";
    }

    @Override
    @Transactional
    @CacheEvict(value = {"expressions_list"}, allEntries = true)
    @CachePut(value = "expressions", key = "#id")
    public ExpressionDto changeExpressionStatus(UUID id, Integer status) {
        Expression entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found with id " + id));
        entity.setStatus(status);
        entity.setLastUpdate(LocalDateTime.now());
        Expression updated = repository.save(entity);
        return ExpressionMapper.toDto(updated);
    }
}
