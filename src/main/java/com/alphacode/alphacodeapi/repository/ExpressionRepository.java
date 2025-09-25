package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Expression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, UUID> {
    Page<Expression> findAllByStatus(Integer status, Pageable pageable);

    @Query("SELECT e FROM Expression e WHERE e.status = 1 ORDER BY e.createdDate DESC")
    Page<Expression> findAllActiveExpressions(Pageable pageable);

    @Query("SELECT e FROM Expression e WHERE e.status = 1 AND (LOWER(e.name) " +
            "LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(e.code) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY e.createdDate DESC")
    Page<Expression> findAllByNameOrCodeContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Modifying
    @Query("UPDATE Expression e SET e.status = 0 WHERE e.id = :id")
    void softDeleteById(@Param("id") UUID id);
}
