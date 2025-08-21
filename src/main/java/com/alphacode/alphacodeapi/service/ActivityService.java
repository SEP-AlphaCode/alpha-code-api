package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for Activity operations.
 */
public interface ActivityService {

    /**
     * Get all activities with pagination and optional status filter.
     *
     * @param page   the page number (1-based)
     * @param size   the page size
     * @param status the status to filter by (optional)
     * @return a paged result of ActivityDto objects
     */
    PagedResult<ActivityDto> getAll(int page, int size, Integer status);

    /**
     * Get an activity by ID.
     *
     * @param id the ID of the activity to retrieve
     * @return the ActivityDto if found
     * @throws com.alphacode.alphacodeapi.exception.ResourceNotFoundException if the activity is not found
     */
    ActivityDto getActivityById(UUID id);

    /**
     * Create a new activity.
     *
     * @param activityDto the activity data to create
     * @return the created ActivityDto
     */
    ActivityDto createActivity(ActivityDto activityDto);

    /**
     * Update an existing activity.
     *
     * @param id         the ID of the activity to update
     * @param activityDto the updated activity data
     * @return the updated ActivityDto
     * @throws com.alphacode.alphacodeapi.exception.ResourceNotFoundException if the activity is not found
     */
    ActivityDto updateActivity(UUID id, ActivityDto activityDto);

    /**
     * Delete an activity by ID.
     *
     * @param id the ID of the activity to delete
     * @throws com.alphacode.alphacodeapi.exception.ResourceNotFoundException if the activity is not found
     */
    void deleteActivity(UUID id);

    /**
     * Get all activities by organization ID with pagination.
     *
     * @param organizationId the organization ID to filter by
     * @param pageable      pagination information
     * @return a page of ActivityDto objects for the given organization
     */
    Page<ActivityDto> getActivitiesByOrganizationId(UUID organizationId, Pageable pageable);

    /**
     * Get all activities by status with pagination.
     *
     * @param status   the status to filter by
     * @param pageable pagination information
     * @return a page of ActivityDto objects with the given status
     */
    Page<ActivityDto> getActivitiesByStatus(Integer status, Pageable pageable);
}
