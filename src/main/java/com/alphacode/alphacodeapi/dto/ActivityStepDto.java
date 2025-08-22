package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityStepDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String type;
    private Integer startTime;
    private Float duration;

    private UUID expressionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String expressionName;

    private UUID actionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String actionName;

    private UUID danceId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String danceName;

    private UUID activityId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String activityName;

    private UUID rgbId;
}