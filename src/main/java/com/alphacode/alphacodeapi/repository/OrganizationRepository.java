package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Page<Organization> findAllByStatus(Integer status, Pageable pageable);
}
