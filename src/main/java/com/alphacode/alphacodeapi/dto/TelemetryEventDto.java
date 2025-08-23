package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelemetryEventDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private UUID robotId;

    private UUID activityId;

    private Integer eventType;

    private Float latency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createDate;
}
