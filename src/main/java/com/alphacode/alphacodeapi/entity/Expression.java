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
@Table(name = "expressions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expression {
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

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "icon_url", nullable = false, length = 255)
    private String iconUrl;

    @OneToMany(mappedBy = "expression", fetch = FetchType.LAZY)
    private List<OsmoCard> osmoCards;

    @OneToMany(mappedBy = "expression", fetch = FetchType.LAZY)
    private List<ActivityDetail> activityDetails;
}