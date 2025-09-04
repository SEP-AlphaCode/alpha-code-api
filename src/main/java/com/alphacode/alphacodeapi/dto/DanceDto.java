package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.DanceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DanceDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Dance code is required")
    @Size(max = 50, message = "Dance code must not exceed 50 characters")
    private String code;

    @NotBlank(message = "Dance name is required")
    @Size(max = 100, message = "Dance name must not exceed 100 characters")
    private String name;

    private String description;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<OsmoCardDto> osmoCards;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ActivityStepDto> activitySteps;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return DanceEnum.fromCode(this.status);
    }

}
