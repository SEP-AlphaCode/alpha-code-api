package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "student_parents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentParent {
    @EmbeddedId
    private StudentParentId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @MapsId("parentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Account parent;
}