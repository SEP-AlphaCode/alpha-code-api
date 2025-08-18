package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
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
    @EmbeddedId
    private TeacherClassId id;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @MapsId("teacherId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Account teacher;

    @MapsId("classId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private Class aClass;
}