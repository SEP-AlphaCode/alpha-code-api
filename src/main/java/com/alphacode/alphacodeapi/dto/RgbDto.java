package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RgbDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Red value is required")
    @Min(value = 0, message = "Red must be >= 0")
    @Max(value = 255, message = "Red must be <= 255")
    private Integer r;

    @NotNull(message = "Red value is required")
    @Min(value = 0, message = "Red must be >= 0")
    @Max(value = 255, message = "Red must be <= 255")
    private Integer g;

    @NotNull(message = "Red value is required")
    @Min(value = 0, message = "Red must be >= 0")
    @Max(value = 255, message = "Red must be <= 255")
    private Integer b;
}
