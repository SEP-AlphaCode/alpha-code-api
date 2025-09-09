package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RgbDto;
import com.alphacode.alphacodeapi.entity.Rgb;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RgbMapper;
import com.alphacode.alphacodeapi.repository.RgbRepository;
import com.alphacode.alphacodeapi.service.RgbService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RgbServiceImpl implements RgbService {

    private final RgbRepository repository;

    @Override
    @Cacheable(value = "rgb_list", key = "{#page, #size}")
    public PagedResult<RgbDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Rgb> pageResult;

        pageResult = repository.findAll(pageable);

        return new PagedResult<>(pageResult.map(RgbMapper::toDto));
    }

    @Override
    @Cacheable(value = "rgb", key = "#id")
    public RgbDto getById(UUID id) {
        var rgb = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rgb not found"));
        return RgbMapper.toDto(rgb);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"rgb_list", "rgb"}, allEntries = true)
    public RgbDto create(RgbDto dto) {
        var entity = RgbMapper.toEntity(dto);

        var saved = repository.save(entity);
        return RgbMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"rgb_list"}, allEntries = true)
    @CachePut(value = "rgb", key = "#id")
    public RgbDto update(UUID id, RgbDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rgb not found"));

        existing.setR(dto.getR());
        existing.setG(dto.getG());
        existing.setB(dto.getB());

        var updated = repository.save(existing);
        return RgbMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"rgb_list"}, allEntries = true)
    @CachePut(value = "rgb", key = "#id")
    public RgbDto patchUpdate(UUID id, RgbDto dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rgb not found"));

        if (dto.getR() != null) {
            existing.setR(dto.getR());
        }
        if (dto.getG() != null) {
            existing.setG(dto.getG());
        }
        if (dto.getB() != null) {
            existing.setB(dto.getB());
        }

        var updated = repository.save(existing);
        return RgbMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"rgb_list", "rgb"}, allEntries = true)
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
}
