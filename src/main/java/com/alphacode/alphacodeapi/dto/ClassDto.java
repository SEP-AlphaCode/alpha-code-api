package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.ClassEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<TeacherClassDto> teachers;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return ClassEnum.fromCode(this.status);
    }
}
