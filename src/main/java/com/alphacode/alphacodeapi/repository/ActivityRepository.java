package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for Activity entity.
 * Provides CRUD operations and custom query methods for Activity entities.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    Page<Activity> findAllByStatus(Integer status, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    Page<Activity> findAllByOrganizationId(UUID organizationId, Pageable pageable);

    Page<Activity> findAllByOrganizationIdAndStatus(UUID organizationId, Integer status, Pageable pageable);
}
