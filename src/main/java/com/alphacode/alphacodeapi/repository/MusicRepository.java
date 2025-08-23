package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicRepository extends JpaRepository<Music, UUID> {

    Page<Music> findAllByStatus(Integer status, Pageable pageable);
}
