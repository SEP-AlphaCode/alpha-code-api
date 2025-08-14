package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.QRCodeMapper;
import com.alphacode.alphacodeapi.repository.QRCodeRepository;
import com.alphacode.alphacodeapi.service.QRCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private final QRCodeRepository repository;
    private final S3ServiceImpl s3Service;
    private final ObjectMapper objectMapper;

    @Override
    public PagedResult<QRCodeDto> getAll(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<QRCode> pageResult;

        if (status != null) {
            pageResult = repository.findAllByStatus(status, pageable);
        } else {
            pageResult = repository.findAll(pageable);
        }
        return new PagedResult<>(pageResult.map(QRCodeMapper::toDto));
    }

    @Override
    public QRCodeDto getById(UUID id) {
        var qrCode = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
        return QRCodeMapper.toDto(qrCode);
    }

    @Override
    public QRCodeDto create(QRCodeDto qrCodeDto) {
        if (qrCodeDto == null || qrCodeDto.getQrCode() == null) {
            throw new IllegalArgumentException("QRCodeDto và các trường không được null");
        }
        if (getByCode(qrCodeDto.getQrCode()) != null) {
            throw new IllegalArgumentException("QRCode with this code already exists");
        }
        try {
            QRCode entity = QRCodeMapper.toEntity(qrCodeDto);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setStatus(1);

            String fileName = "qr_" + entity.getQrCode() + "_" + System.currentTimeMillis() + ".png";
            String imageUrl = generateAndUploadQRCode(entity.getQrCode(), fileName);
            entity.setImageUrl(imageUrl);

            QRCode savedEntity = repository.save(entity);
            return QRCodeMapper.toDto(savedEntity);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Lỗi khi tạo hoặc tải QR code", e);
        }
    }




//    @Override
//    public QRCodeDto update(UUID id, QRCodeDto qrCodeDto) throws JsonProcessingException {
//        var existed = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
//
//        existed.setQrCode(qrCodeDto.getCode());
//
//        if (qrCodeDto.getData() != null) {
//            existed.set(objectMapper.writeValueAsString(qrCodeDto.getData()));
//        }
//
//        existed.setLastEdited(LocalDateTime.now());
//
//        QRCode savedEntity = repository.save(existed);
//        return QRCodeMapper.toDto(savedEntity);
//    }

    @Override
    public QRCodeDto update(UUID id, QRCodeDto qrCodeDto) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));

        existed.setQrCode(qrCodeDto.getQrCode());

        existed.setLastEdited(LocalDateTime.now());

        QRCode savedEntity = repository.save(existed);
        return QRCodeMapper.toDto(savedEntity);
    }

    @Override
    public void delete(UUID id) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
//        repository.deleteById(id);
        existed.setStatus(0);
        existed.setLastEdited(LocalDateTime.now());
        repository.save(existed);
    }

    @Override
    public QRCodeDto getByCode(String code) {
        var existed = repository.findQRCodeByQrCode(code).getId();
        return repository.findById(existed)
                .map(QRCodeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));

    }

    @Override
    public QRCodeDto changeStatus(UUID id, Integer status) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
        if (status != null) {
            existed.setStatus(status);
        } else {
            throw new IllegalArgumentException("Status cannot be null");
        }
        existed.setLastEdited(LocalDateTime.now());
        QRCode savedEntity = repository.save(existed);
        return QRCodeMapper.toDto(savedEntity);
    }

    private String generateAndUploadQRCode(String text, String fileName) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Lưu vào memory thay vì file local
        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // Upload lên S3 và trả về URL
            return s3Service.uploadBytes(pngData, "qrcodes/" + fileName, "image/png");
        }
    }



}
