package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.RobotPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RobotPermissionRepository extends JpaRepository<RobotPermission, UUID> {
    Page<RobotPermission> findAllByStatus(Integer status, Pageable pageable);
}
