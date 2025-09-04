package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "teachers_classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherClass {

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "teacher_id", nullable = false, columnDefinition = "uuid")
    private UUID teacherId;

    @Column(name = "class_id", nullable = false, columnDefinition = "uuid")
    private UUID classId;

    @Column(name = "accountsrole_id", nullable = false, columnDefinition = "uuid")
    private UUID accountsRoleId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Account teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private ClassEntity classEntity;
}
