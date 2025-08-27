package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.ActivityStepDto;
import com.alphacode.alphacodeapi.entity.ActivityStep;

public class ActivityStepMapper {

    public static ActivityStepDto toDto(ActivityStep step) {
        if (step == null) {
            return null;
        }
        ActivityStepDto dto = new ActivityStepDto();
        dto.setId(step.getId());
        dto.setType(step.getType());
        dto.setStartTime(step.getStartTime());
        dto.setDuration(step.getDuration());

        dto.setExpressionId(step.getExpressionId());
        dto.setActionId(step.getActionId());
        dto.setDanceId(step.getDanceId());
        dto.setActivityId(step.getActivityId());
        dto.setRgbId(step.getRgbId());

        // Map tên từ quan hệ
        if (step.getExpression() != null) {
            dto.setExpressionName(step.getExpression().getName());
        }
        if (step.getAction() != null) {
            dto.setActionName(step.getAction().getName());
        }
        if (step.getDance() != null) {
            dto.setDanceName(step.getDance().getName());
        }
        if (step.getActivity() != null) {
            dto.setActivityName(step.getActivity().getName());
        }
        if (step.getRgb() != null) {
            dto.setRgbId(step.getRgb().getId()); // giữ nguyên id vì dto chỉ có id
        }

        return dto;
    }

    public static ActivityStep toEntity(ActivityStepDto dto) {
        if (dto == null) {
            return null;
        }
        ActivityStep step = new ActivityStep();
        step.setId(dto.getId());
        step.setType(dto.getType());
        step.setStartTime(dto.getStartTime());
        step.setDuration(dto.getDuration());

        step.setExpressionId(dto.getExpressionId());
        step.setActionId(dto.getActionId());
        step.setDanceId(dto.getDanceId());
        step.setActivityId(dto.getActivityId());
        step.setRgbId(dto.getRgbId());

        // Chỉ set ID, không map object vì tránh lazy load / recursion
        return step;
    }
}
