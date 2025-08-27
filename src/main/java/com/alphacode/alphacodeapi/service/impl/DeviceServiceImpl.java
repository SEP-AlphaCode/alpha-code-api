package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.DeviceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Device;
import com.alphacode.alphacodeapi.entity.Music;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.DeviceMapper;
import com.alphacode.alphacodeapi.mapper.MusicMapper;
import com.alphacode.alphacodeapi.repository.DeviceRepository;
import com.alphacode.alphacodeapi.repository.MusicRepository;
import com.alphacode.alphacodeapi.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    @Override
    public PagedResult<DeviceDto> getAll(UUID spaceId, int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Device> pageResult;

        if (spaceId != null && status != null) {
            pageResult = repository.findAllBySpaceIdAndStatus(spaceId, status, pageable);
        } else if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(DeviceMapper::toDto));
    }

    @Override
    public DeviceDto getById(UUID id) {
        Device entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return DeviceMapper.toDto(entity);
    }

    @Override
    public DeviceDto create(DeviceDto dto) {
        Device entity = DeviceMapper.toEntity(dto);

        entity.setCreatedDate(LocalDateTime.now());

        Device saved = repository.save(entity);
        return DeviceMapper.toDto(saved);
    }

    @Override
    public DeviceDto update(UUID id, DeviceDto dto) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        existing.setSpaceId(dto.getSpaceId());
        existing.setType(dto.getType());
        existing.setIpConfig(dto.getIpConfig());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setStatus(dto.getStatus());

        Device updated = repository.save(existing);
        return DeviceMapper.toDto(updated);
    }

    @Override
    public DeviceDto patchUpdate(UUID id, DeviceDto dto) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (dto.getSpaceId() != null) {
            existing.setSpaceId(dto.getSpaceId());
        }
        if (dto.getType() != null) {
            existing.setType(dto.getType());
        }
        if (dto.getIpConfig() != null) {
            existing.setIpConfig(dto.getIpConfig());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        existing.setLastUpdate(LocalDateTime.now());

        Device updated = repository.save(existing);
        return DeviceMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        existing.setStatus(0);
        existing.setLastUpdate(LocalDateTime.now());

        repository.save(existing);

        return "Deleted Device with ID: " + id;
    }
}
