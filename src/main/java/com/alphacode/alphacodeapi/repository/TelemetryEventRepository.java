package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.TelemetryEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelemetryEventRepository extends JpaRepository<TelemetryEvent, UUID> {
    Page<TelemetryEvent> findAll(Pageable pageable);
    Page<TelemetryEvent> findAllByRobotId(UUID robotId, Pageable pageable);
}
