package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.TeacherClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherClassRepository extends JpaRepository<TeacherClass, UUID> {
    Page<TeacherClass> findAllByStatus(Integer status, Pageable pageable);
}
