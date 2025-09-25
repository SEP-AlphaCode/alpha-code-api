package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.alphacode.alphacodeapi.exception.ResourceNotFoundException;
import com.alphacode.alphacodeapi.mapper.QRCodeMapper;
import com.alphacode.alphacodeapi.repository.QRCodeRepository;
import com.alphacode.alphacodeapi.service.ActivityService;
import com.alphacode.alphacodeapi.service.QRCodeService;
import com.alphacode.alphacodeapi.service.S3Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private final QRCodeRepository repository;
    private final S3Service s3Service;
    private final ActivityService activityService;
    private final ObjectMapper objectMapper;

    @Override
    @Cacheable(value = "qr_codes_list", key = "{#page, #size, #status}")
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
    @Cacheable(value = "qr_codes", key = "#id")
    public QRCodeDto getById(UUID id) {
        var qrCode = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
        return QRCodeMapper.toDto(qrCode);
    }

    @Override
    public ActivityDto getByQrImage(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File ảnh không được null hoặc rỗng");
            }

            // Đọc ảnh từ MultipartFile
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new IllegalArgumentException("File ảnh không hợp lệ hoặc không thể đọc");
            }

            // Decode QR code bằng ZXing
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);

            String decodedText = result.getText();
            if (decodedText == null || decodedText.isBlank()) {
                throw new IllegalArgumentException("Không tìm thấy nội dung QR code trong ảnh");
            }

            // Dùng lại logic getByCode
            QRCodeDto qrCodeDto = getByCode(decodedText);

            if (qrCodeDto == null) throw new ResourceNotFoundException("QR code không tồn tại");

            ActivityDto activityDto = activityService.getById(qrCodeDto.getActivityId());

            if (activityDto == null) throw new ResourceNotFoundException("Activity không tồn tại");

            return activityDto;

        } catch (NotFoundException e) {
            throw new ResourceNotFoundException("Không tìm thấy QR code trong ảnh");
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file ảnh QR code", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi decode QR code", e);
        }
    }



    @Override
    @Transactional
    @CacheEvict(value = {"qr_codes_list", "qr_codes"}, allEntries = true)
    public QRCodeDto create(QRCodeDto qrCodeDto) {
        if (qrCodeDto == null || qrCodeDto.getQrCode() == null) {
            throw new IllegalArgumentException("QRCodeDto và các trường không được null");
        }

        if (qrCodeDto.getAccountId() == null) {
            throw new IllegalArgumentException("AccountId không được null");
        }

        if (qrCodeDto.getActivityId() == null) {
            throw new IllegalArgumentException("ActivityId không được null");
        }

        if (repository.findQRCodeByQrCode(qrCodeDto.getQrCode()).isPresent()) {
            throw new IllegalArgumentException("QRCode với mã này đã tồn tại");
        }

        try {
            QRCode entity = QRCodeMapper.toEntity(qrCodeDto);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setStatus(qrCodeDto.getStatus() != null ? qrCodeDto.getStatus() : 1);

            // Tạo QR code và upload S3
            String fileName = "qr_" + entity.getQrCode() + "_" + System.currentTimeMillis() + ".png";
            String imageUrl = generateAndUploadQRCode(entity.getQrCode(), fileName);
            entity.setImageUrl(imageUrl);

            QRCode saved = repository.save(entity);
            return QRCodeMapper.toDto(saved);
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
    @Transactional
    @CacheEvict(value = {"qr_codes_list"}, allEntries = true)
    @CachePut(value = "qr_codes", key = "#id")
    public QRCodeDto update(UUID id, QRCodeDto qrCodeDto) {
        var existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));

        existed.setName(qrCodeDto.getName());
        existed.setColor(qrCodeDto.getColor());
        existed.setQrCode(qrCodeDto.getQrCode());
        existed.setStatus(qrCodeDto.getStatus());
        existed.setImageUrl(qrCodeDto.getImageUrl());
        if (qrCodeDto.getActivityId() != null) {
            existed.setActivityId(qrCodeDto.getActivityId());
        }
        if (qrCodeDto.getAccountId() != null) {
            existed.setAccountId(qrCodeDto.getAccountId());
        }

        existed.setLastEdited(LocalDateTime.now());

        QRCode savedEntity = repository.save(existed);
        return QRCodeMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"qr_codes_list"}, allEntries = true)
    @CachePut(value = "qr_codes", key = "#id")
    public QRCodeDto patchUpdate(UUID id, QRCodeDto qrCodeDto) {
        QRCode existed = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found with id " + id));

        boolean regenerateImage = false;

        if (qrCodeDto.getQrCode() != null) {
            existed.setQrCode(qrCodeDto.getQrCode());
        }
        if (qrCodeDto.getColor() != null) {
            existed.setColor(qrCodeDto.getColor());
        }
        if (qrCodeDto.getName() != null) {
            existed.setName(qrCodeDto.getName());
        }
        if (qrCodeDto.getQrCode() != null && !qrCodeDto.getQrCode().equals(existed.getQrCode())) {
            existed.setQrCode(qrCodeDto.getQrCode());
            regenerateImage = true;
        }
        if (qrCodeDto.getAccountId() != null) {
            existed.setAccountId(qrCodeDto.getAccountId());
        }
        if (qrCodeDto.getActivityId() != null) {
            existed.setActivityId(qrCodeDto.getActivityId());
        }

        // regenerate image nếu cần
        if (regenerateImage) {
            String fileName = "qr_" + existed.getQrCode() + "_" + System.currentTimeMillis() + ".png";
            try {
                String imageUrl = generateAndUploadQRCode(existed.getQrCode(), fileName);
                if (imageUrl == null) {
                    throw new RuntimeException("Không tạo được QR image");
                }
                existed.setImageUrl(imageUrl);
            } catch (WriterException | IOException e) {
                throw new RuntimeException("Lỗi khi tạo lại QRCode image", e);
            }
        }

        existed.setLastEdited(LocalDateTime.now());

        return QRCodeMapper.toDto(repository.save(existed));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"qr_codes_list", "qr_codes"}, key = "#id", allEntries = true)
    public String delete(UUID id) {
        try {
            var existed = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
//        repository.deleteById(id);
            existed.setStatus(0);
            existed.setLastEdited(LocalDateTime.now());
            repository.save(existed);
            return "Deleted QRCode with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting QRCode", e);
        }

    }

    @Override
    @Transactional
    @CacheEvict(value = {"qr_codes_list", "qr_codes"}, key = "#id", allEntries = true)
    public String disable(UUID id) {
        try {
            var existed = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
//        repository.deleteById(id);
            existed.setStatus(2);
            existed.setLastEdited(LocalDateTime.now());
            repository.save(existed);
            return "Disable QRCode with ID: " + id;
        } catch (Exception e) {
            throw new RuntimeException("Error disable QRCode", e);
        }

    }

    @Override
    @Cacheable(value = "qr_codes", key = "#code")
    public QRCodeDto getByCode(String code) {
        return repository.findQRCodeByQrCode(code)
                .map(QRCodeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("QRCode not found"));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"qr_codes_list"}, allEntries = true)
    @CachePut(value = "qr_codes", key = "#id")
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
