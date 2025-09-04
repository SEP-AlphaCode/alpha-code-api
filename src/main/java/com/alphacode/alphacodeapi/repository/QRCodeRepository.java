package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.QRCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {
    Page<QRCode> findAllByStatus(Integer status, Pageable pageable);

    Optional<QRCode> findQRCodeByQrCode(String qrCode);
}
