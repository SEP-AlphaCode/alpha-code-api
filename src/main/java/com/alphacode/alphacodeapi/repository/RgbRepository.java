package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Rgb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RgbRepository extends JpaRepository<Rgb, UUID> {
    Page<Rgb> findAll(UUID robotId, Pageable pageable);
}
