package com.alphacode.alphacodeapi.entity;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "space_id", nullable = false, columnDefinition = "uuid")
    private UUID spaceId;

    @Column(name = "device_name", nullable = false, length = 255)
    private String deviceName;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "topic_sub", nullable = false, length = 255)
    private String topicSub;

    @Column(name = "topic_pub", length = 255)
    private String topicPub;

    @Column(name = "ip_config", nullable = false)
    private String ipConfig;

    @Type(JsonType.class)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private JsonNode metadata;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @Column(name = "power_state", nullable = false)
    private Boolean powerState; // false = off, true = on (dùng khi điều khiển MQTT)

    @Column(name = "status", nullable = false)
    private Integer status;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false, insertable = false, updatable = false)
    private Space space;
}