package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.QRCodeMapper;
import com.alphacode.alphacodeapi.repository.QRCodeRepository;
import com.alphacode.alphacodeapi.service.QRCodeService;
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

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private final QRCodeRepository repository;
    private final S3ServiceImpl s3Service;

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
    public QRCodeDto getById(Integer id) {
        var qrCode = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
        return QRCodeMapper.toDto(qrCode);
    }

    @Override
    public QRCodeDto create(QRCodeDto qrCodeDto) {
        try {
            QRCode entity = QRCodeMapper.toEntity(qrCodeDto);
            entity.setCreatedDate(LocalDateTime.now());

            // Tạo QR code và upload lên S3
            String fileName = "qr_" + System.currentTimeMillis() + ".png";
            String imageUrl = generateAndUploadQRCode(entity.getCode(), fileName);

            entity.setImageUrl(imageUrl);

            QRCode savedEntity = repository.save(entity);
            return QRCodeMapper.toDto(savedEntity);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code image", e);
        }
    }

    @Override
    public QRCodeDto update(Integer id, QRCodeDto qrCodeDto) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
        existed.setCode(qrCodeDto.getCode());
        existed.setAction(qrCodeDto.getAction());
        existed.setExpression(qrCodeDto.getExpression());
        existed.setVoice(qrCodeDto.getVoice());
        existed.setStatus(qrCodeDto.getStatus());
        existed.setLastEdited(LocalDateTime.now());
        existed.setImageUrl(qrCodeDto.getImageUrl());
        QRCode savedEntity = repository.save(existed);
        return QRCodeMapper.toDto(savedEntity);
    }

    @Override
    public void delete(Integer id) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
//        repository.deleteById(id);
        existed.setStatus(0);
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
