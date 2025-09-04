package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "musics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Music {
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

    @Column(name = "url", nullable = false, length = 255)
    private String url; // .wav file

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @OneToMany(mappedBy = "music", fetch = FetchType.LAZY)
    private List<Activity> activities;
}