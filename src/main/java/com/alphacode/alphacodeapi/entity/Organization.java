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
@Table(name = "organizations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {
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

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", nullable = false, length = 255)
    private String phone;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Activity> activities;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Space> spaces;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Robot> robots;
}