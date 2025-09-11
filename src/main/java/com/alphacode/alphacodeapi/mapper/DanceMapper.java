package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.entity.ActivityStep;
import com.alphacode.alphacodeapi.entity.Dance;
import com.alphacode.alphacodeapi.entity.OsmoCard;

import java.util.stream.Collectors;

public class DanceMapper {

    public static DanceDto toDto(Dance dance) {
        if (dance == null) {
            return null;
        }

        DanceDto dto = new DanceDto();
        dto.setId(dance.getId());
        dto.setCode(dance.getCode());
        dto.setName(dance.getName());
        dto.setDescription(dance.getDescription());
        dto.setStatus(dance.getStatus());
        dto.setLastUpdate(dance.getLastUpdate());
        dto.setCreatedDate(dance.getCreatedDate());
        dto.setDuration(dance.getDuration());

        return dto;
    }

    public static Dance toEntity(DanceDto dto) {
        if (dto == null) {
            return null;
        }
        Dance dance = new Dance();
        dance.setId(dto.getId());
        dance.setCode(dto.getCode());
        dance.setName(dto.getName());
        dance.setDescription(dto.getDescription());
        dance.setStatus(dto.getStatus());
        dance.setLastUpdate(dto.getLastUpdate());
        dance.setCreatedDate(dto.getCreatedDate());
        dance.setDuration(dto.getDuration());
        return dance;
    }

    private static OsmoCardDto toOsmoCardDto(OsmoCard osmoCard) {
        if (osmoCard == null) return null;

        OsmoCardDto dto = new OsmoCardDto();
        dto.setId(osmoCard.getId());
        dto.setName(osmoCard.getName());
        dto.setColor(osmoCard.getColor());
        dto.setStatus(osmoCard.getStatus());
        dto.setLastUpdate(osmoCard.getLastUpdate());
        dto.setCreatedDate(osmoCard.getCreatedDate());

        if (osmoCard.getExpression() != null) {
            dto.setExpressionId(osmoCard.getExpression().getId());
            dto.setExpressionName(osmoCard.getExpression().getName());
        }
        if (osmoCard.getAction() != null) {
            dto.setActionId(osmoCard.getAction().getId());
            dto.setActionName(osmoCard.getAction().getName());
        }
        if (osmoCard.getDance() != null) {
            dto.setDanceId(osmoCard.getDance().getId());
            dto.setDanceName(osmoCard.getDance().getName());
        }

        return dto;
    }

    private static ActivityStepDto toActivityStepDto(ActivityStep step) {
        if (step == null) return null;

        ActivityStepDto dto = new ActivityStepDto();
        dto.setId(step.getId());
        dto.setType(step.getType());
        dto.setStartTime(step.getStartTime());
        dto.setDuration(step.getDuration());

        if (step.getExpression() != null) {
            dto.setExpressionId(step.getExpression().getId());
            dto.setExpressionName(step.getExpression().getName());
        }
        if (step.getAction() != null) {
            dto.setActionId(step.getAction().getId());
            dto.setActionName(step.getAction().getName());
        }
        if (step.getDance() != null) {
            dto.setDanceId(step.getDance().getId());
            dto.setDanceName(step.getDance().getName());
        }
        if (step.getActivity() != null) {
            dto.setActivityId(step.getActivity().getId());
            dto.setActivityName(step.getActivity().getName());
        }
        if (step.getRgb() != null) {
            dto.setRgbId(step.getRgb().getId());
        }

        return dto;
    }
}
