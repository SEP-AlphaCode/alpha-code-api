package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.RgbDto;
import com.alphacode.alphacodeapi.entity.Rgb;

public class RgbMapper {
    public static RgbDto toDto(Rgb entity) {
        if (entity == null) {
            return null;
        }
        RgbDto dto = new RgbDto();
        dto.setId(entity.getId());
        dto.setR(entity.getR());
        dto.setG(entity.getG());
        dto.setB(entity.getB());
        return dto;
    }

    public static Rgb toEntity(RgbDto dto) {
        if (dto == null) {
            return null;
        }
        Rgb entity = new Rgb();
        entity.setId(dto.getId());
        entity.setR(dto.getR());
        entity.setG(dto.getG());
        entity.setB(dto.getB());
        return entity;
    }
}
