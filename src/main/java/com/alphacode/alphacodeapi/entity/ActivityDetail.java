package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "activity_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "start_time", nullable = false)
    private Integer startTime;

    @Column(name = "duration", nullable = false)
    private Float duration;

    @Column(name = "expression_id", columnDefinition = "uuid")
    private UUID expressionId;

    @Column(name = "action_id", columnDefinition = "uuid")
    private UUID actionId;

    @Column(name = "dance_id", columnDefinition = "uuid")
    private UUID danceId;

    @Column(name = "activity_id", nullable = false, columnDefinition = "uuid")
    private UUID activityId;

    @Column(name = "rgb_id", nullable = false, columnDefinition = "uuid")
    private UUID rgbId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expression_id", insertable = false, updatable = false)
    private Expression expression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", insertable = false, updatable = false)
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dance_id", insertable = false, updatable = false)
    private Dance dance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", insertable = false, updatable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rgb_id", insertable = false, updatable = false)
    private Rgb rgb;
}