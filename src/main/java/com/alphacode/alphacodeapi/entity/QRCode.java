package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "qr_code", nullable = false, unique = true, length = 255)
    private String code;

    @Column(name = "action", nullable = true, length = 255)
    private String action;

    @Column(name = "expression", nullable = true, length = 255)
    private String expression;

    @Column(name = "voice", nullable = true, length = 255)
    private String voice;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_edited", nullable = true)
    private LocalDateTime lastEdited;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;
}
