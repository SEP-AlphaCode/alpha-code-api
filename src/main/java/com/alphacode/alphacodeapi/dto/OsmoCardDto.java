package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OsmoCardDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String color;

    private String name;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    private UUID expressionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String expressionName;

    private UUID actionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String actionName;

    private UUID danceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String danceName;
}
