package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.ActivityDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDetailRepository extends JpaRepository<ActivityDetail, Integer> {

    // Filter theo type (contains, ignore case) để paging
    Page<ActivityDetail> findAllByTypeContainingIgnoreCase(String type, Pageable pageable);
}