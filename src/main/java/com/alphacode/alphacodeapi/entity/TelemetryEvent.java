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
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "telemetry_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelemetryEvent {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "robot_id", nullable = false, columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID robotId;

    @Column(name = "activity_id", nullable = false, columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID activityId;

    @Column(name = "event_type", nullable = false)
    private Integer eventType;

    @Column(name = "latency", nullable = false)
    private Float latency;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_id", nullable = false)
    private Robot robot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
}
