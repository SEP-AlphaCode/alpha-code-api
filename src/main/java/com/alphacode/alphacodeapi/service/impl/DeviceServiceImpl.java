package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.DeviceDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Device;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.DeviceMapper;
import com.alphacode.alphacodeapi.repository.DeviceRepository;
import com.alphacode.alphacodeapi.service.DeviceService;
import com.alphacode.alphacodeapi.service.MqttService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final MqttService mqttService;

    @Override
    @Cacheable(value = "devices_list", key = "{#spaceId, #page, #size, #status}")
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
    @Cacheable(value = "devices", key = "#id")
    public DeviceDto getById(UUID id) {
        Device entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return DeviceMapper.toDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"devices_list", "devices"}, allEntries = true)
    public DeviceDto create(DeviceDto dto) {
        Device entity = DeviceMapper.toEntity(dto);

        entity.setCreatedDate(LocalDateTime.now());

        Device saved = repository.save(entity);
        return DeviceMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"devices_list"}, allEntries = true)
    @CachePut(value = "devices", key = "#id")
    public DeviceDto update(UUID id, DeviceDto dto) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        existing.setSpaceId(dto.getSpaceId());
        existing.setDeviceName(dto.getDeviceName());
        existing.setTopicSub(dto.getTopicSub());
        existing.setTopicPub(dto.getTopicPub());
        existing.setMetadata(dto.getMetadata());
        existing.setType(dto.getType());
        existing.setIpConfig(dto.getIpConfig());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setStatus(dto.getStatus());
        existing.setLastSeen(dto.getLastSeen());
        existing.setPowerState(dto.getPowerState());

        Device updated = repository.save(existing);
        return DeviceMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"devices_list"}, allEntries = true)
    @CachePut(value = "devices", key = "#id")
    public DeviceDto patchUpdate(UUID id, DeviceDto dto) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (dto.getSpaceId() != null) {
            existing.setSpaceId(dto.getSpaceId());
        }
        if (dto.getDeviceName() != null) {
            existing.setDeviceName(dto.getDeviceName());
        }
        if (dto.getTopicSub() != null) {
            existing.setTopicSub(dto.getTopicSub());
        }
        if (dto.getTopicPub() != null) {
            existing.setTopicPub(dto.getTopicPub());
        }
        if (dto.getMetadata() != null) {
            existing.setMetadata(dto.getMetadata());
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
        if (dto.getLastSeen() != null) {
            existing.setLastSeen(dto.getLastSeen());
        }
        if (dto.getPowerState() != null) {
            existing.setPowerState(dto.getPowerState());
        }
        existing.setLastUpdate(LocalDateTime.now());

        Device updated = repository.save(existing);
        return DeviceMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"devices_list", "devices"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        existing.setStatus(0);
        existing.setLastUpdate(LocalDateTime.now());

        repository.save(existing);

        return "Deleted Device with ID: " + id;
    }

    @Override
    @Transactional
    @CachePut(value = "devices", key = "#id")
    @CacheEvict(value = {"devices_list"}, allEntries = true)
    public DeviceDto changeDeviceStatus(UUID id, Integer status) {
        Device existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        existing.setStatus(status);
        existing.setLastUpdate(LocalDateTime.now());
        Device updated = repository.save(existing);
        return DeviceMapper.toDto(updated);
    }

    @Override
    @Transactional
    public DeviceDto updateDeviceState(UUID id, Boolean powerState) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found " + id));

        // Update DB
        device.setPowerState(powerState);
        device.setLastUpdate(LocalDateTime.now());
        Device updated = repository.save(device);

        // Publish MQTT
        String topic = "devices/" + device.getId() + "/control";
        String payload = powerState ? "ON" : "OFF";
        try {
            mqttService.publish(topic, payload);
            System.out.printf("MQTT published: topic=%s, payload=%s%n", topic, payload);
        } catch (MqttException e) {
            throw new RuntimeException("Failed to publish MQTT message", e);
        }

        return DeviceMapper.toDto(updated);
    }
}
