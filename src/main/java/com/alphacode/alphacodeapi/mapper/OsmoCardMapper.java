package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.entity.OsmoCard;

public class OsmoCardMapper {
    public static OsmoCardDto toDto(OsmoCard osmoCard) {
        if (osmoCard == null) {
            return null;
        }
        OsmoCardDto dto = new OsmoCardDto();
        dto.setId(osmoCard.getId());
        dto.setColor(osmoCard.getColor());
        dto.setName(osmoCard.getName());
        dto.setStatus(osmoCard.getStatus());
        dto.setLastUpdate(osmoCard.getLastUpdate());
        dto.setCreateDate(osmoCard.getCreateDate());
        dto.setExpressionId(osmoCard.getExpressionId());
        if (osmoCard.getExpression() != null) {
            dto.setExpressionName(osmoCard.getExpression().getName());
        }
        dto.setActionId(osmoCard.getActionId());
        if (osmoCard.getAction() != null) {
            dto.setActionName(osmoCard.getAction().getName());
        }
        dto.setDanceId(osmoCard.getDanceId());
        if (osmoCard.getDance() != null) {
            dto.setDanceName(osmoCard.getDance().getName());
        }
        return dto;
    }

    public static OsmoCard toEntity(OsmoCardDto osmoCardDto) {
        if (osmoCardDto == null) {
            return null;
        }
        OsmoCard osmoCard = new OsmoCard();
        osmoCard.setId(osmoCardDto.getId());
        osmoCard.setColor(osmoCardDto.getColor());
        osmoCard.setName(osmoCardDto.getName());
        osmoCard.setStatus(osmoCardDto.getStatus());
        osmoCard.setLastUpdate(osmoCardDto.getLastUpdate());
        osmoCard.setCreateDate(osmoCardDto.getCreateDate());
        osmoCard.setExpressionId(osmoCardDto.getExpressionId());
        osmoCard.setActionId(osmoCardDto.getActionId());
        osmoCard.setDanceId(osmoCardDto.getDanceId());
        return osmoCard;
    }
}
