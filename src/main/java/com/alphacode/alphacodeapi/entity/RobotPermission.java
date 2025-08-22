package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "robot_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RobotPermission {

    @EmbeddedId
    private RobotPermissionId id;  // PK gồm id + robot_id + activity_id

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // ---- Quan hệ ----
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("robotId")
    @JoinColumn(name = "robot_id", nullable = false)
    private Robot robot;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activityId")
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    // ---- Composite key class ----
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RobotPermissionId implements Serializable {
        @Column(name = "id", columnDefinition = "uuid")
        private UUID id;

        @Column(name = "robot_id", columnDefinition = "uuid")
        private UUID robotId;

        @Column(name = "activity_id", columnDefinition = "uuid")
        private UUID activityId;
    }
}