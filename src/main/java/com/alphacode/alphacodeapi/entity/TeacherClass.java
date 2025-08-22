package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "teachers_classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherClass {

    @EmbeddedId
    private TeacherClassId id;  // PK: teacher_id + class_id

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "accountsrole_id", nullable = false, columnDefinition = "uuid")
    private UUID accountsRoleId;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id", nullable = false)
    private Account teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("classId")
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    // ---- Composite key ----
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeacherClassId implements Serializable {
        @Column(name = "teacher_id", columnDefinition = "uuid")
        private UUID teacherId;

        @Column(name = "class_id", columnDefinition = "uuid")
        private UUID classId;
    }
}