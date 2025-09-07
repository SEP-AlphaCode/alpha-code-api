package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDetailDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String rgbName;
}
