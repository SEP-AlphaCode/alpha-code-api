package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.entity.ClassEntity;
import com.alphacode.alphacodeapi.entity.Music;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.MusicMapper;
import com.alphacode.alphacodeapi.repository.MusicRepository;
import com.alphacode.alphacodeapi.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository repository;
    private static final String MUSIC_NOT_FOUND = "Music not found";

    @Override
    public PagedResult<MusicDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Music> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }

        return new PagedResult<>(pageResult.map(MusicMapper::toDto));
    }

    @Override
    public MusicDto getById(UUID id) {
        Music music = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MUSIC_NOT_FOUND));
        return MusicMapper.toDto(music);
    }

    @Override
    public MusicDto create(MusicDto dto) {
        Music music = MusicMapper.toEntity(dto);
        music.setCreateDate(LocalDateTime.now());
        music.setLastUpdate(LocalDateTime.now());
        Music saved = repository.save(music);
        return MusicMapper.toDto(saved);
    }

    @Override
    public MusicDto update(UUID id, MusicDto dto) {
        Music existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MUSIC_NOT_FOUND));

        existing.setName(dto.getName());
        existing.setUrl(dto.getUrl());
        existing.setDuration(dto.getDuration());
        existing.setStatus(dto.getStatus());
        existing.setImage(dto.getImage());
        existing.setLastUpdate(LocalDateTime.now());

        if (dto.getClassId() != null) {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(dto.getClassId());
            existing.setClassEntity(classEntity);
        }

        Music updated = repository.save(existing);
        return MusicMapper.toDto(updated);
    }

    @Override
    public MusicDto patchUpdate(UUID id, MusicDto dto) {
        Music existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MUSIC_NOT_FOUND));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getUrl() != null) existing.setUrl(dto.getUrl());
        if (dto.getDuration() != null) existing.setDuration(dto.getDuration());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getImage() != null) existing.setImage(dto.getImage());

        if (dto.getClassId() != null) {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(dto.getClassId());
            existing.setClassEntity(classEntity);
        }

        existing.setLastUpdate(LocalDateTime.now());
        Music updated = repository.save(existing);
        return MusicMapper.toDto(updated);
    }

    @Override
    public String delete(UUID id) {
        Music existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MUSIC_NOT_FOUND));

        // Soft delete: set status = 0
        existing.setStatus(0);
        existing.setLastUpdate(LocalDateTime.now());
        repository.save(existing);

        return "Deleted Music with ID: " + id;
    }
}