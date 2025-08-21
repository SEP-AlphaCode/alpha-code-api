package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.Account;
import com.alphacode.alphacodeapi.entity.OsmoCard;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.AccountMapper;
import com.alphacode.alphacodeapi.mapper.OsmoCardMapper;
import com.alphacode.alphacodeapi.mapper.RoleMapper;
import com.alphacode.alphacodeapi.repository.OsmoCardRepository;
import com.alphacode.alphacodeapi.service.OsmoCardService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OsmoCardServiceImpl implements OsmoCardService {

    private final OsmoCardRepository repository;

    @Override
    public PagedResult<OsmoCardDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OsmoCard> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }
        return new PagedResult<>(pageResult.map(OsmoCardMapper::toDto));
    }

    @Override
    public OsmoCardDto getById(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OsmoCard not found"));
        return OsmoCardMapper.toDto(entity);
    }

    @Override
    public OsmoCardDto create(OsmoCardDto dto) {
        var entity = OsmoCardMapper.toEntity(dto);
        if (entity.getActionId() == null && entity.getDanceId() == null && entity.getExpressionId() == null) {
            throw new IllegalArgumentException("Osmo Card must have at least one of Action ID, Dance ID, or Expression ID set");
        } else if (entity.getActionId() != null && entity.getDanceId() != null && entity.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have all Action ID, Dance ID, and Expression ID set at the same time");
        } else if (entity.getActionId() != null && entity.getDanceId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Dance ID set at the same time");
        } else if (entity.getActionId() != null && entity.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Expression ID set at the same time");
        } else if (entity.getDanceId() != null && entity.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Dance ID and Expression ID set at the same time");
        }

        entity.setColor(dto.getColor());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setLastUpdate(LocalDateTime.now());
        entity.setCreateDate(LocalDateTime.now());
        if (entity.getActionId() != null) {
            entity.setActionId(entity.getActionId());
        } else if (entity.getDanceId() != null) {
            entity.setDanceId(entity.getDanceId());
        } else if (entity.getExpressionId() != null) {
            entity.setExpressionId(entity.getExpressionId());
        }


        var saved = repository.save(entity);
        return OsmoCardMapper.toDto(saved);
    }

    @Override
    public OsmoCardDto update(UUID id, OsmoCardDto dto) {
        if (dto.getActionId() == null && dto.getDanceId() == null && dto.getExpressionId() == null) {
            throw new IllegalArgumentException("Osmo Card must have at least one of Action ID, Dance ID, or Expression ID set");
        } else if (dto.getActionId() != null && dto.getDanceId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have all Action ID, Dance ID, and Expression ID set at the same time");
        } else if (dto.getActionId() != null && dto.getDanceId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Dance ID set at the same time");
        } else if (dto.getActionId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Expression ID set at the same time");
        } else if (dto.getDanceId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Dance ID and Expression ID set at the same time");
        }

        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OsmoCard not found"));

        existing.setName(dto.getName());
        existing.setColor(dto.getColor());
        existing.setStatus(dto.getStatus());
        existing.setLastUpdate(LocalDateTime.now());
        existing.setExpressionId(dto.getExpressionId());
        existing.setActionId(dto.getActionId());
        existing.setDanceId(dto.getDanceId());


        var updated = repository.save(existing);
        return OsmoCardMapper.toDto(updated);
    }

    @Override
    public OsmoCardDto patchUpdate(UUID id, OsmoCardDto dto) {
        if (dto.getActionId() != null && dto.getDanceId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have all Action ID, Dance ID, and Expression ID set at the same time");
        } else if (dto.getActionId() != null && dto.getDanceId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Dance ID set at the same time");
        } else if (dto.getActionId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Action ID and Expression ID set at the same time");
        } else if (dto.getDanceId() != null && dto.getExpressionId() != null) {
            throw new IllegalArgumentException("Osmo Card cannot have both Dance ID and Expression ID set at the same time");
        }

        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OsmoCard not found"));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getColor() != null) {
            existing.setColor(dto.getColor());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getExpressionId() != null) {
            existing.setExpressionId(dto.getExpressionId());
        }
        if (dto.getActionId() != null) {
            existing.setActionId(dto.getActionId());
        }
        if (dto.getDanceId() != null) {
            existing.setDanceId(dto.getDanceId());
        }
        existing.setLastUpdate(LocalDateTime.now());

        var updated = repository.save(existing);
        return OsmoCardMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        try {
            var existing = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("OsmoCard not found"));

            existing.setStatus(0);
            repository.save(existing);
            return "Deleted OsmoCard with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting OsmoCard", e);
        }

    }
}
