package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);

    Page<Role> findAllByStatus(Integer status, Pageable pageable);
}
