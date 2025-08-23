package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.entity.Music;

public class MusicMapper {

    public static MusicDto toDto(Music music) {
        if (music == null) return null;

        MusicDto dto = new MusicDto();
        dto.setId(music.getId());
        dto.setName(music.getName());
        dto.setUrl(music.getUrl());
        dto.setCreateDate(music.getCreateDate());
        dto.setLastUpdate(music.getLastUpdate());
        dto.setDuration(music.getDuration());
        dto.setStatus(music.getStatus());
        dto.setClassId(music.getClassId());
        dto.setImage(music.getImage());

        // Nếu muốn map thêm tên class có thể thêm như AccountMapper
        // if (music.getAClass() != null) {
        //     dto.setClassName(music.getAClass().getName());
        // }

        return dto;
    }

    public static Music toEntity(MusicDto dto) {
        if (dto == null) return null;

        Music music = new Music();
        music.setId(dto.getId());
        music.setName(dto.getName());
        music.setUrl(dto.getUrl());
        music.setCreateDate(dto.getCreateDate());
        music.setLastUpdate(dto.getLastUpdate());
        music.setDuration(dto.getDuration());
        music.setStatus(dto.getStatus());
        music.setClassId(dto.getClassId());
        music.setImage(dto.getImage());

        return music;
    }
}