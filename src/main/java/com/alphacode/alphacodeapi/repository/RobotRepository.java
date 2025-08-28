package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Robot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RobotRepository extends JpaRepository<Robot, UUID> {
    Page<Robot> findAllByStatusAndOrganizationId(Integer status, UUID organizationId, Pageable pageable);

    Page<Robot> findAllByStatus(Integer status, Pageable pageable);

    Page<Robot> findAllByOrganizationId(UUID organizationId, Pageable pageable);
}
