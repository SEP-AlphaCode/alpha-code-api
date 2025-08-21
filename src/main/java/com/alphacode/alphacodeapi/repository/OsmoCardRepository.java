package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.OsmoCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OsmoCardRepository extends JpaRepository<OsmoCard, UUID> {
    Page<OsmoCard> findAllByStatus(Integer status, Pageable pageable);
}
