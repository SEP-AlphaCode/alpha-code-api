package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.QRCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {
    @Query("SELECT a FROM QRCode a WHERE a.status != 0 ORDER BY a.createdDate DESC")
    Page<QRCode> findAllByStatus(Integer status, Pageable pageable);

    @Query("SELECT a FROM QRCode a WHERE a.status != 0 AND a.qrCode = :qrCode ORDER BY a.createdDate DESC")
    Optional<QRCode> findQRCodeByQrCode(String qrCode);
}
