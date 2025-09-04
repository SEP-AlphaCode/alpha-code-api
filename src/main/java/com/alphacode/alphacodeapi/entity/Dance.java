package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dances")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dance {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;
    @Column(name = "code", nullable = false, length = 100, unique = true)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    // ---- Quan hệ ----
    @OneToMany(mappedBy = "dance", fetch = FetchType.LAZY)
    private List<ActivityStep> activitySteps;

    @OneToMany(mappedBy = "dance", fetch = FetchType.LAZY)
    private List<OsmoCard> osmoCards;
}
