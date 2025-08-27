package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.TelemetryEventDto;
import com.alphacode.alphacodeapi.entity.TelemetryEvent;

public class TelemetryEventMapper {
    public static TelemetryEventDto toDto(TelemetryEvent telemetryEvent) {
        if (telemetryEvent == null) {
            return null;
        }

        TelemetryEventDto telemetryEventDto = new TelemetryEventDto();
        telemetryEventDto.setId(telemetryEvent.getId());
        telemetryEventDto.setRobotId(telemetryEvent.getRobotId());
        if (telemetryEvent.getRobot() != null) {
            telemetryEventDto.setRobotName(telemetryEvent.getRobot().getName());
        }
        telemetryEventDto.setActivityId(telemetryEvent.getActivityId());
        if (telemetryEvent.getActivity() != null) {
            telemetryEventDto.setActivityName(telemetryEvent.getActivity().getName());
        }
        telemetryEventDto.setEventType(telemetryEvent.getEventType());
        telemetryEventDto.setLatency(telemetryEvent.getLatency());
        telemetryEventDto.setCreatedDate(telemetryEvent.getCreatedDate());
        return telemetryEventDto;
    }

    public static TelemetryEvent toEntity(TelemetryEventDto telemetryEventDto) {
        if (telemetryEventDto == null) {
            return null;
        }

        TelemetryEvent telemetryEvent = new TelemetryEvent();
        telemetryEvent.setId(telemetryEventDto.getId());
        telemetryEvent.setRobotId(telemetryEventDto.getRobotId());
        telemetryEvent.setActivityId(telemetryEventDto.getActivityId());
        telemetryEvent.setEventType(telemetryEventDto.getEventType());
        telemetryEvent.setLatency(telemetryEventDto.getLatency());
        telemetryEvent.setCreatedDate(telemetryEventDto.getCreatedDate());
        return telemetryEvent;
    }
}
