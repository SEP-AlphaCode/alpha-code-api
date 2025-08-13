package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "qr_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QRCode {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;


    @Column(name = "qr_code", nullable = false, unique = true, length = 255)
    private String code;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String data;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_edited", nullable = true)
    private LocalDateTime lastEdited;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;
}
