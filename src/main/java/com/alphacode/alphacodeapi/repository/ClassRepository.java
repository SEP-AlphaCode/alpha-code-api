package com.alphacode.alphacodeapi.repository;


import com.alphacode.alphacodeapi.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassRepository extends JpaRepository<ClassEntity, UUID> {
    Page<ClassEntity> findAllByStatus(Integer status, Pageable pageable);
}