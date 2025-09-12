package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.RobotEnum;
import com.alphacode.alphacodeapi.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RobotDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Robot name is required", groups = {OnCreate.class})
    @Size(max = 100, message = "Robot name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "IP address is required", groups = {OnCreate.class})
    @Pattern(
            regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Invalid IP address format"
    )
    private String ip;

    @NotBlank(message = "Robot code is required", groups = {OnCreate.class})
    @Size(max = 50, message = "Robot code must not exceed 50 characters")
    private String code;

    @NotBlank(message = "Type is required", groups = {OnCreate.class})
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;

    @NotNull(message = "Organization ID is required", groups = {OnCreate.class})
    private UUID organizationId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String organizationName;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return RobotEnum.fromCode(this.status);
    }

}
