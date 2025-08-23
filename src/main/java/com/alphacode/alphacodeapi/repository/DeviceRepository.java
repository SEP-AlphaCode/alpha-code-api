package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Page<Device> findAllByStatus(Integer status, Pageable pageable);
    Page<Device> findAllBySpaceIdAndStatus(UUID spaceId, Integer status, Pageable pageable);
}
