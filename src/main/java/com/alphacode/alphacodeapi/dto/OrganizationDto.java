package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.OrganizationEnum;
import com.alphacode.alphacodeapi.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class OrganizationDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Organization name is required", groups = {OnCreate.class})
    @Size(max = 150, message = "Organization name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "Location is required", groups = {OnCreate.class})
    @Size(max = 255, message = "Location must not exceed 255 characters")
    private String location;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;

    @NotBlank(message = "Email is required", groups = {OnCreate.class})
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{9,15}$",
            message = "Phone number must contain 9–15 digits"
    )
    private String phone;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return OrganizationEnum.fromCode(this.status);
    }
}
