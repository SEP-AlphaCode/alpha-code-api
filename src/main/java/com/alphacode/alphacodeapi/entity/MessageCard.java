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
@Table(name = "message_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageCard {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = true, columnDefinition = "text")
    private String content;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update", nullable = true)
    private LocalDateTime lastUpdate;

    @Column(name = "color", nullable = true, length = 255)
    private String color;

    @Column(name = "emotion_id", nullable = true, columnDefinition = "uuid")
    private UUID emotionId;

    @Column(name = "depression_id", nullable = true, columnDefinition = "uuid")
    private UUID depressionId;

    @Column(name = "stance_id", nullable = true, columnDefinition = "uuid")
    private UUID stanceId;
}