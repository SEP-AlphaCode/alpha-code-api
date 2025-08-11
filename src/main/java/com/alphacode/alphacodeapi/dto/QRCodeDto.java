package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String code;

    private String action;

    private String expression;

    private String voice;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastEdited;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;
}
