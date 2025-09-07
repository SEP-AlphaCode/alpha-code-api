package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityStepDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Start time is required")
    @Min(value = 0, message = "Start time must be >= 0")
    private Integer startTime;

    @NotNull(message = "Duration is required")
    @Min(value = 0, message = "Duration must be >= 0")
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