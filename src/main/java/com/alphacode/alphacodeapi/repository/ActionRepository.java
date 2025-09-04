package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<Action, UUID> {
    @Query("SELECT a FROM Action a WHERE LOWER(a.name) " +
            "LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(a.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY a.createdDate DESC")
    Page<Action> findAllByNameOrDescriptionContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
}
