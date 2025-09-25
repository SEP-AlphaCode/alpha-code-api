package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.DeviceEnum;
import com.alphacode.alphacodeapi.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class DeviceDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Space ID is required", groups = {OnCreate.class})
    private UUID spaceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String spaceName;

    @NotBlank(message = "Device name is required", groups = {OnCreate.class})
    private String deviceName;

    @NotBlank(message = "Device type is required", groups = {OnCreate.class})
    @Size(max = 50, message = "Device type must not exceed 50 characters")
    private String type;

    @NotBlank(message = "Topic Sub is required", groups = {OnCreate.class})
    @Size(max = 50, message = "Topic Sub must not exceed 50 characters")
    private String topicSub;

    @NotBlank(message = "Topic Pub is required", groups = {OnCreate.class})
    @Size(max = 50, message = "Topic Pub must not exceed 50 characters")
    private String topicPub;


    @NotBlank(message = "IP Config is required", groups = {OnCreate.class})
    @Pattern(
            regexp = "^((25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})(\\.|$)){4}$",
            message = "IP Config must be a valid IPv4 address"
    )
    private String ipConfig;

    private JsonNode metadata;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastSeen;

    private Boolean powerState;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return DeviceEnum.fromCode(this.status);
    }
}
