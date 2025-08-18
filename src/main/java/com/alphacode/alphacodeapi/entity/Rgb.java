package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "rgbs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rgb {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "r", nullable = false)
    private Integer r;

    @Column(name = "g", nullable = false)
    private Integer g;

    @Column(name = "b", nullable = false)
    private Integer b;
}