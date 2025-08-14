package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherClassId implements Serializable {
    private UUID teacherId;
    private UUID classId;
}