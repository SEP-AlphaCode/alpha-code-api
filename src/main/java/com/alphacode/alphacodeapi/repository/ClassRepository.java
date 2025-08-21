package com.alphacode.alphacodeapi.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ClassRepository extends JpaRepository<com.alphacode.alphacodeapi.entity.Class, UUID> {
    Page<com.alphacode.alphacodeapi.entity.Class> findAllByStatus(Integer status, Pageable pageable);
}