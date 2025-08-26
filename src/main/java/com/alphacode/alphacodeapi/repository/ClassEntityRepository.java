package com.alphacode.alphacodeapi.repository;


import com.alphacode.alphacodeapi.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, UUID> {
    Page<ClassEntity> findAllByStatus(Integer status, Pageable pageable);
}