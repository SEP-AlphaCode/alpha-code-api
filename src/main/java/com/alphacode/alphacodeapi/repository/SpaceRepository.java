package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {
    Page<Space> findAllByStatus(Integer status, Pageable pageable);
}
