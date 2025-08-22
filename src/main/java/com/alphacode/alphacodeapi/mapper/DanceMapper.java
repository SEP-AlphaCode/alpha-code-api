package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.DanceDto;
import com.alphacode.alphacodeapi.dto.OsmoCardDto;
import com.alphacode.alphacodeapi.dto.ActivityDetailDto;
import com.alphacode.alphacodeapi.entity.Dance;
import com.alphacode.alphacodeapi.entity.OsmoCard;
import com.alphacode.alphacodeapi.entity.ActivityDetail;

import java.util.List;
import java.util.stream.Collectors;

public class DanceMapper {

    public static DanceDto toDto(Dance dance) {
        if (dance == null) {
            return null;
        }
        DanceDto dto = new DanceDto();
        dto.setId(dance.getId());
        dto.setName(dance.getName());
        dto.setDescription(dance.getDescription());
        dto.setStatus(dance.getStatus());
        dto.setLastUpdate(dance.getLastUpdate());
        dto.setCreateDate(dance.getCreateDate());
        dto.setDuration(dance.getDuration());

        // Map osmoCards nếu cần
        if (dance.getOsmoCards() != null) {
            List<OsmoCardDto> osmoCardDtos = dance.getOsmoCards().stream()
                    .map(DanceMapper::toOsmoCardDto)
                    .collect(Collectors.toList());
            // dto.setOsmoCards(osmoCardDtos); // cần thêm List<OsmoCardDto> vào DanceDto nếu muốn
        }

        // Map activityDetails nếu cần
        if (dance.getActivityDetails() != null) {
            List<ActivityDetailDto> activityDetailDtos = dance.getActivityDetails().stream()
                    .map(DanceMapper::toActivityDetailDto)
                    .collect(Collectors.toList());
            // dto.setActivityDetails(activityDetailDtos); // cần thêm List<ActivityDetailDto> vào DanceDto nếu muốn
        }

        return dto;
    }

    public static Dance toEntity(DanceDto dto) {
        if (dto == null) {
            return null;
        }
        Dance dance = new Dance();
        dance.setId(dto.getId());
        dance.setName(dto.getName());
        dance.setDescription(dto.getDescription());
        dance.setStatus(dto.getStatus());
        dance.setLastUpdate(dto.getLastUpdate());
        dance.setCreateDate(dto.getCreateDate());
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
        dto.setCreateDate(osmoCard.getCreateDate());

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

    private static ActivityDetailDto toActivityDetailDto(ActivityDetail detail) {
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
}
