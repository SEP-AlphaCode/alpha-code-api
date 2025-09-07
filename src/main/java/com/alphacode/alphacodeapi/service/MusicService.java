package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MusicService {
    PagedResult<MusicDto> getAll(int page, int size, Integer status);

    MusicDto getById(UUID id);

    MusicDto create(MusicDto dto, MultipartFile urlFile, MultipartFile imageFile);

    MusicDto update(UUID id, MusicDto dto);

    MusicDto patchUpdate(UUID id, MusicDto dto);

    String delete(UUID id);

    MusicDto changeMusicStatus(UUID id, Integer status);
}
