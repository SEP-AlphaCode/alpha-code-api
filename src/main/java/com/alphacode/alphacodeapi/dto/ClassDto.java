package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClassDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer status;


}
