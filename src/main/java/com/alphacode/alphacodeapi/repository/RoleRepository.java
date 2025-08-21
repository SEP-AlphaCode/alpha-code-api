package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
    Page<Role> findAllByStatus(Integer status, Pageable pageable);
}
