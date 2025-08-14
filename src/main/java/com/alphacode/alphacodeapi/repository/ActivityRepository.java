package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for Activity entity.
 * Provides CRUD operations and custom query methods for Activity entities.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    /**
     * Find all activities with pagination and status filter.
     *
     * @param status   the status to filter by (can be null to ignore status filter)
     * @param pageable pagination information
     * @return a page of activities matching the criteria
     */
    Page<Activity> findAllByStatus(Integer status, Pageable pageable);

    /**
     * Check if an activity with the given name already exists.
     *
     * @param name the name to check
     * @return true if an activity with the name exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Check if an activity with the given name exists, excluding the activity with the given ID.
     *
     * @param name the name to check
     * @param id   the ID of the activity to exclude
     * @return true if another activity with the name exists, false otherwise
     */
    boolean existsByNameAndIdNot(String name, UUID id);

    /**
     * Find all activities by organization ID with pagination.
     *
     * @param organizationId the organization ID to filter by
     * @param pageable      pagination information
     * @return a page of activities for the given organization
     */
    Page<Activity> findAllByOrganizationId(UUID organizationId, Pageable pageable);

    /**
     * Find all activities by organization ID and status with pagination.
     *
     * @param organizationId the organization ID to filter by
     * @param status        the status to filter by
     * @param pageable      pagination information
     * @return a page of activities matching the criteria
     */
    Page<Activity> findAllByOrganizationIdAndStatus(UUID organizationId, Integer status, Pageable pageable);
}
