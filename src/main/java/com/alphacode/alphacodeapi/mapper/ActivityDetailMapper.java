package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActivityDetailDto;
import com.alphacode.alphacodeapi.entity.ActivityDetail;

public class ActivityDetailMapper {

    public static ActivityDetailDto toDto(ActivityDetail detail) {
        if (detail == null) return null;
        ActivityDetailDto dto = new ActivityDetailDto();
        dto.setId(detail.getId());
        dto.setType(detail.getType());
        dto.setStartTime(detail.getStartTime());
        dto.setDuration(detail.getDuration());
        dto.setExpressionId(detail.getExpressionId());
        dto.setActionId(detail.getActionId());
        dto.setDanceId(detail.getDanceId());
        dto.setActivityId(detail.getActivityId());
        dto.setRgbId(detail.getRgbId());

        if (detail.getExpression() != null) dto.setExpressionName(detail.getExpression().getName());
        if (detail.getAction() != null) dto.setActionName(detail.getAction().getName());
        if (detail.getDance() != null) dto.setDanceName(detail.getDance().getName());
        if (detail.getActivity() != null) dto.setActivityName(detail.getActivity().getName());
        if (detail.getRgb() != null) dto.setRgbId(detail.getRgb().getId());

        return dto;
    }

    public static ActivityDetail toEntity(ActivityDetailDto dto) {
        if (dto == null) return null;
        ActivityDetail detail = new ActivityDetail();
        detail.setId(dto.getId());
        detail.setType(dto.getType());
        detail.setStartTime(dto.getStartTime());
        detail.setDuration(dto.getDuration());
        detail.setExpressionId(dto.getExpressionId());
        detail.setActionId(dto.getActionId());
        detail.setDanceId(dto.getDanceId());
        detail.setActivityId(dto.getActivityId());
        detail.setRgbId(dto.getRgbId());
        return detail;
    }
}