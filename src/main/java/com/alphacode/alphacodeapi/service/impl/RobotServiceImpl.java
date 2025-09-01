package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.RgbDto;
import com.alphacode.alphacodeapi.dto.RobotDto;
import com.alphacode.alphacodeapi.entity.Robot;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.RobotMapper;
import com.alphacode.alphacodeapi.repository.RobotRepository;
import com.alphacode.alphacodeapi.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {

    private final RobotRepository repository;

    @Override
    public PagedResult<RobotDto> getAll(int page, int size, UUID organizationId, Integer status) {
        Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
        Page<Robot> pageResult;

        if (status != null && organizationId != null) {
            pageResult = repository.findAllByStatusAndOrganizationId(status, organizationId, pageable);
        } else if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else if (organizationId != null) {
            pageResult = repository.findAllByOrganizationId(organizationId, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(RobotMapper::toDto));
    }

    @Override
    public RobotDto getById(UUID id) {
        var robot = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Robot not found"));
        return RobotMapper.toDto(robot);
    }

    @Override
    @Transactional
    public RobotDto create(RobotDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Robot data must not be null");
        }

        try {
            Robot robot = RobotMapper.toEntity(dto);
            Robot savedRobot = repository.save(robot);
            savedRobot.setCreatedDate(LocalDateTime.now());
            return RobotMapper.toDto(savedRobot);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create robot", e);
        }
    }

    @Override
    @Transactional
    public RobotDto update(UUID id, RobotDto dto) {
        var existingRobot = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Robot not found"));

        existingRobot.setName(dto.getName());
        existingRobot.setIp(dto.getIp());
        existingRobot.setCode(dto.getCode());
        existingRobot.setType(dto.getType());
        existingRobot.setStatus(dto.getStatus());
        existingRobot.setOrganizationId(dto.getOrganizationId());
        existingRobot.setLastUpdate(LocalDateTime.now());
        Robot updatedRobot = repository.save(existingRobot);
        return RobotMapper.toDto(updatedRobot);
    }

    @Override
    @Transactional
    public RobotDto patchUpdate(UUID id, RobotDto dto) {
        var existingRobot = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Robot not found"));

        if (dto.getName() != null) {
            existingRobot.setName(dto.getName());
        }
        if (dto.getIp() != null) {
            existingRobot.setIp(dto.getIp());
        }
        if (dto.getCode() != null) {
            existingRobot.setCode(dto.getCode());
        }
        if (dto.getType() != null) {
            existingRobot.setType(dto.getType());
        }
        if (dto.getStatus() != null) {
            existingRobot.setStatus(dto.getStatus());
        }
        if (dto.getOrganizationId() != null) {
            existingRobot.setOrganizationId(dto.getOrganizationId());
        }

        existingRobot.setLastUpdate(LocalDateTime.now());
        Robot updatedRobot = repository.save(existingRobot);
        return RobotMapper.toDto(updatedRobot);
    }

    @Override
    @Transactional
    public String delete(UUID id) {
        try {
            var robot = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Robot not found"));

            robot.setStatus(0);
            repository.save(robot);
            return "Robot deleted successfully with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting robot", e);
        }
    }
}
