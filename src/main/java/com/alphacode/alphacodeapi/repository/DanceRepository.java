package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Dance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DanceRepository extends JpaRepository<Dance, UUID> {
    @Query("SELECT d FROM Dance d WHERE d.status = 1 AND (LOWER(d.name) " +
            "LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY d.createdDate DESC")
    Page<Dance> findPagedDances(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT d FROM Dance d WHERE d.status = 1 ORDER BY d.createdDate DESC")
    Page<Dance> findAllActiveDances(Pageable pageable);

    @Modifying
    @Query("UPDATE Dance d SET d.status = 0 WHERE d.id = :id")
    void softDeleteById(@Param("id") UUID id);
}
