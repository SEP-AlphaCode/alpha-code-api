package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "activity_steps")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityStep {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "start_time", nullable = false)
    private Integer startTime;

    @Column(name = "duration", nullable = false)
    private Float duration;

    @Column(name = "expression_id", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID expressionId;

    @Column(name = "action_id", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID actionId;

    @Column(name = "dance_id", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID danceId;

    @Column(name = "activity_id", nullable = false, columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID activityId;

    @Column(name = "rgb_id", nullable = false, columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID rgbId;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expression_id")
    private Expression expression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dance_id")
    private Dance dance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rgb_id", nullable = false)
    private Rgb rgb;
}