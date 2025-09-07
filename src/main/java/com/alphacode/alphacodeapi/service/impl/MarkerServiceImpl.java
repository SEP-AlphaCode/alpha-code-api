package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.MarkerDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Marker;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.MarkerMapper;
import com.alphacode.alphacodeapi.repository.MarkerRepository;
import com.alphacode.alphacodeapi.service.MarkerService;
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
public class MarkerServiceImpl implements MarkerService {
    private final MarkerRepository repository;

    @Override
    @Cacheable(value = "markers_list", key = "{#page, #size, #status}")
    public PagedResult<MarkerDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Marker> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(MarkerMapper::toDto));
    }

    @Override
    @Cacheable(value = "markers", key = "#id")
    public MarkerDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marker not found"));
        return MarkerMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"markers_list", "markers"}, allEntries = true)
    public MarkerDto create(MarkerDto dto) {
        var entity = MarkerMapper.toEntity(dto);

        entity.setCreatedDate(LocalDateTime.now());

        var saved = repository.save(entity);
        return MarkerMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"markers_list"}, allEntries = true)
    @CachePut(value = "markers", key = "#id")
    public MarkerDto update(UUID id, MarkerDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marker not found"));
        existing.setName(dto.getName());
        existing.setStatus(dto.getStatus());
        existing.setLastEdited(LocalDateTime.now());
        existing.setActivityId(dto.getActivityId());
        var updated = repository.save(existing);
        return MarkerMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"markers_list"}, allEntries = true)
    @CachePut(value = "markers", key = "#id")
    public MarkerDto patchUpdate(UUID id, MarkerDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marker not found"));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getActivityId() != null) {
            existing.setActivityId(dto.getActivityId());
        }

        existing.setLastEdited(LocalDateTime.now());

        var updated = repository.save(existing);
        return MarkerMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"markers", "markers_list"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        try {
            var existing = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Rgb not found"));

            repository.delete(existing);

            return "Rgb deleted successfully with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting rgb", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"markers_list"}, allEntries = true)
    @CachePut(value = "markers", key = "#id")
    public MarkerDto changeMarkerStatus(UUID id, Integer status) {
        Marker entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marker not found"));

        entity.setStatus(status);
        entity.setLastEdited(LocalDateTime.now());

        Marker updated = repository.save(entity);
        return MarkerMapper.toDto(updated);
    }

}
