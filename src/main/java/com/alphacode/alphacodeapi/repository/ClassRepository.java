package com.alphacode.alphacodeapi.repository;


import com.alphacode.alphacodeapi.entity.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ClassRepository extends JpaRepository<SchoolClass, UUID> {
    Page<SchoolClass> findAllByStatus(Integer status, Pageable pageable);
}