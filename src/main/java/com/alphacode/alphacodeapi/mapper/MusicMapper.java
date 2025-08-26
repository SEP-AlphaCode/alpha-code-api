package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.MusicDto;
import com.alphacode.alphacodeapi.entity.ClassEntity;
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

        // map classId từ classEntity
        if (music.getClassEntity() != null) {
            dto.setClassId(music.getClassEntity().getId());
            // Nếu muốn map thêm tên class
            // dto.setClassName(music.getClassEntity().getName());
        }

        dto.setImage(music.getImage());
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

        // map classEntity từ classId
        if (dto.getClassId() != null) {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(dto.getClassId());
            music.setClassEntity(classEntity);
        }

        music.setImage(dto.getImage());
        return music;
    }
}
