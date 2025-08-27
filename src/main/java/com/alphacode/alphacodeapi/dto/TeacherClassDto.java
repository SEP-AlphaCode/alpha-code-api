package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.DanceEnum;
import com.alphacode.alphacodeapi.enums.TeacherClassEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TeacherClassDto {
    private UUID teacherId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String teacherName;

    private UUID classId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String className;

    private Integer status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty("statusText")
    public String getStatusText() {
        return TeacherClassEnum.fromCode(this.status);
    }
}