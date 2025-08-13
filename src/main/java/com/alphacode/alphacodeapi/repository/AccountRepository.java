package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Page<Account> findAllByStatus(Integer status, Pageable pageable);
}
