package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.entity.Class;
import com.alphacode.alphacodeapi.entity.StudentParent;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
public class StudentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String fullName;

    private UUID classId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String facialRecordData;

    private String shortName;

    private String nickname;

    private LocalDateTime dateOfBirth;

    private Integer gender;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String image;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String className;

}
