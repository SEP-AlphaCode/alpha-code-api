package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status", nullable = false)
    private Integer status;

    // ---- Quan hệ ----
    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private List<Music> musics;

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.LAZY)
    private List<TeacherClass> teacherClasses;
}