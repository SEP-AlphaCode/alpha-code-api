package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.ActivityStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityStepRepository extends JpaRepository<ActivityStep, UUID> {
    Page<ActivityStep> findAllByActivityId(UUID activityId, Pageable pageable);
}