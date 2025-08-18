package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.entity.Activity;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.ActivityMapper;
import com.alphacode.alphacodeapi.repository.ActivityRepository;
import com.alphacode.alphacodeapi.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private static final String ACTIVITY_NOT_FOUND = "Activity not found with id: ";

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDto> getAllActivities(Pageable pageable) {
        log.debug("Fetching all activities with pagination - page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return activityRepository.findAll(pageable)
                .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityDto getActivityById(UUID id) {
        log.debug("Fetching activity with id: {}", id);
        return activityRepository.findById(id)
                .map(activityMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND + id));
    }

    @Override
    @Transactional
    public ActivityDto createActivity(ActivityDto activityDto) {
        log.debug("Creating new activity with name: {}", activityDto.getName());

        // Không kiểm tra tồn tại nữa
        activityDto.setCreateDate(LocalDateTime.now());

        Activity activity = activityMapper.toEntity(activityDto);
        Activity savedActivity = activityRepository.save(activity);

        log.info("Created new activity with id: {}", savedActivity.getId());
        return activityMapper.toDto(savedActivity);
    }

    @Override
    @Transactional
    public ActivityDto updateActivity(UUID id, ActivityDto activityDto) {
        log.debug("Updating activity with id: {}", id);

        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ACTIVITY_NOT_FOUND + id));

        // Không kiểm tra trùng tên nữa
        Activity updatedActivity = activityMapper.updateEntity(activityDto, existingActivity);
        Activity savedActivity = activityRepository.save(updatedActivity);

        log.info("Updated activity with id: {}", id);
        return activityMapper.toDto(savedActivity);
    }

    @Override
    @Transactional
    public void deleteActivity(UUID id) {
        log.debug("Deleting activity with id: {}", id);

        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException(ACTIVITY_NOT_FOUND + id);
        }

        activityRepository.deleteById(id);
        log.info("Deleted activity with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDto> getActivitiesByOrganizationId(UUID organizationId, Pageable pageable) {
        log.debug("Fetching activities for organization id: {} with pagination - page: {}, size: {}",
                organizationId, pageable.getPageNumber(), pageable.getPageSize());
        return activityRepository.findAllByOrganizationId(organizationId, pageable)
                .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDto> getActivitiesByStatus(Integer status, Pageable pageable) {
        log.debug("Fetching activities with status: {} with pagination - page: {}, size: {}",
                status, pageable.getPageNumber(), pageable.getPageSize());
        return activityRepository.findAllByStatus(status, pageable)
                .map(activityMapper::toDto);
    }
}
