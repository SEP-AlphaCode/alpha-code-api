package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.DeviceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Space ID is required")
    private UUID spaceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String spaceName;

    @NotBlank(message = "Device type is required")
    @Size(max = 50, message = "Device type must not exceed 50 characters")
    private String type;

    @NotBlank(message = "IP Config is required")
    @Pattern(
            regexp = "^((25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})(\\.|$)){4}$",
            message = "IP Config must be a valid IPv4 address"
    )
    private String ipConfig;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return DeviceEnum.fromCode(this.status);
    }
}
