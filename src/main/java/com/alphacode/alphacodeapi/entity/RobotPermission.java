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
@Table(name = "robot_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RobotPermission {

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "robot_id", nullable = false, columnDefinition = "uuid")
    private UUID robotId;

    @Column(name = "activity_id", nullable = false, columnDefinition = "uuid")
    private UUID activityId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_id", insertable = false, updatable = false)
    private Robot robot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", insertable = false, updatable = false)
    private Activity activity;
}
