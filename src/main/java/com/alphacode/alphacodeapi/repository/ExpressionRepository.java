package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Expression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, UUID> {
    Page<Expression> findAllByStatus(Integer status, Pageable pageable);
}
