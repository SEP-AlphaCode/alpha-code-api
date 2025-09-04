package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.AccountEnum;
import com.alphacode.alphacodeapi.enums.ExpressionEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Expression name is required")
    @Size(max = 100, message = "Expression name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Expression code is required")
    @Size(max = 50, message = "Expression code must not exceed 50 characters")
    private String code;

    private String imageUrl;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return ExpressionEnum.fromCode(this.status);
    }
}
