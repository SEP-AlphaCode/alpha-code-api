package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "robots")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Robot {
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

    @Column(name = "ip", nullable = false, length = 255)
    private String ip;

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "robot", fetch = FetchType.LAZY)
    private List<RobotPermission> robotPermissions;
}