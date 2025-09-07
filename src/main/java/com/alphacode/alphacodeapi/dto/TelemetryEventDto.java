package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelemetryEventDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private UUID robotId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String robotName;

    private UUID activityId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String activityName;

    private Integer eventType;

    private Float latency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
}
