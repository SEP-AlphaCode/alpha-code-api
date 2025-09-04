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
@Table(name = "actions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Action {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;
    @Column(name = "code", nullable = false, length = 100, unique = true)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "can_interrupt", nullable = false)
    private Boolean canInterrupt;

    // Quan hệ ngược với ActivityStep
    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY)
    private List<ActivityStep> activitySteps;

    // Quan hệ ngược với OsmoCard
    @OneToMany(mappedBy = "action", fetch = FetchType.LAZY)
    private List<OsmoCard> osmoCards;
}