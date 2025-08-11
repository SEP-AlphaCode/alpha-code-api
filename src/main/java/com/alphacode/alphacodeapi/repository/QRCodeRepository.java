package com.alphacode.alphacodeapi.repository;

import com.alphacode.alphacodeapi.entity.QRCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  QRCodeRepository extends JpaRepository<QRCode, Integer> {
    Page<QRCode> findAllByStatus(Integer status, Pageable pageable);
    QRCode findQRCodeByCode(String code);
}
