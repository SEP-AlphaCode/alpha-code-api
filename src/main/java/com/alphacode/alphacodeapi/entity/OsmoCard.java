package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "osmo_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OsmoCard {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "color", nullable = false, length = 255)
    private String color;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expression_id", columnDefinition = "uuid")
    private UUID expressionId;

    @Column(name = "action_id", columnDefinition = "uuid")
    private UUID actionId;

    @Column(name = "dance_id", columnDefinition = "uuid")
    private UUID danceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expression_id", insertable = false, updatable = false)
    private Expression expression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", insertable = false, updatable = false)
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dance_id", insertable = false, updatable = false)
    private Dance dance;
}