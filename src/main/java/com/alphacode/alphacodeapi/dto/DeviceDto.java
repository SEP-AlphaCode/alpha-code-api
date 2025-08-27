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
public class DeviceDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private UUID spaceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String spaceName;

    private String type;

    private String ipConfig;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;
}
