package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Marker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, UUID> {
    Page<Marker> findAllByStatus(Integer status, Pageable pageable);


}
