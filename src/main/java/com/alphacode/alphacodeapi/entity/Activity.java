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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
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

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Type(JsonType.class)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    private JsonNode data;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "organization_id", nullable = false, columnDefinition = "uuid", updatable = false)
    private UUID organizationId;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "music_id", columnDefinition = "uuid")
    private UUID musicId;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false, insertable = false, updatable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", insertable = false, updatable = false)
    private Music music;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<ActivityStep> steps;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<QRCode> qrCodes;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<Marker> markers;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<RobotPermission> robotPermissions;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<TelemetryEvent> telemetryEvents;
}