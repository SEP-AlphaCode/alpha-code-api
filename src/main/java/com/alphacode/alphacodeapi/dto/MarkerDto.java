package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.entity.Activity;
import com.alphacode.alphacodeapi.enums.DanceEnum;
import com.alphacode.alphacodeapi.enums.MarkerEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Marker name is required")
    @Size(max = 100, message = "Marker name must not exceed 100 characters")
    private String name;

    private Integer status;

    @NotNull(message = "Book ID is required")
    private UUID bookId;

    @NotNull(message = "Page ID is required")
    private Integer pageId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastEdited;

    @NotNull(message = "Activity ID is required")
    private UUID activityId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String activityName;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return MarkerEnum.fromCode(this.status);
    }
}
