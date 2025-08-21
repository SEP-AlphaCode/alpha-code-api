package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class QRCodeMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


//    public static QRCodeDto toDto(com.alphacode.alphacodeapi.entity.QRCode qrCode) {
//        if (qrCode == null) {
//            return null;
//        }
//        QRCodeDto qrCodeDto = new QRCodeDto();
//        qrCodeDto.setId(qrCode.getId());
//        qrCodeDto.setCode(qrCode.getCode());
//        qrCodeDto.setType(qrCode.getType());
////        qrCodeDto.setData(qrCode.getData());
//        try {
//            if (qrCode.getData() != null) {
//                qrCodeDto.setData(objectMapper.readValue(qrCode.getData(), Map.class));
//            }
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Lỗi khi parse JSON data", e);
//        }
//        qrCodeDto.setStatus(qrCode.getStatus());
//        qrCodeDto.setCreatedDate(qrCode.getCreatedDate());
//        qrCodeDto.setLastEdited(qrCode.getLastEdited());
//        qrCodeDto.setImageUrl(qrCode.getImageUrl());
//
//        return qrCodeDto;
//    }
//
//    public  static QRCode toEntity(QRCodeDto qrCodeDto) {
//        if (qrCodeDto == null) {
//            return null;
//        }
//        QRCode qrCode = new QRCode();
//        qrCode.setId(qrCodeDto.getId());
//        qrCode.setCode(qrCodeDto.getCode());
//        qrCode.setType(qrCodeDto.getType());
////        qrCode.setData(qrCodeDto.getData());
//        try {
//            if (qrCodeDto.getData() != null) {
//                qrCode.setData(objectMapper.writeValueAsString(qrCodeDto.getData()));
//            }
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Lỗi khi chuyển data thành JSON", e);
//        }
//        qrCode.setStatus(qrCodeDto.getStatus());
//        qrCode.setCreatedDate(qrCodeDto.getCreatedDate());
//        qrCode.setLastEdited(qrCodeDto.getLastEdited());
//        qrCode.setImageUrl(qrCodeDto.getImageUrl());
//
//        return qrCode;
//    }

public static QRCodeDto toDto(com.alphacode.alphacodeapi.entity.QRCode qrCode) {
    if (qrCode == null) {
        return null;
    }
    QRCodeDto qrCodeDto = new QRCodeDto();
    qrCodeDto.setId(qrCode.getId());
    qrCodeDto.setName(qrCode.getName());
    qrCodeDto.setQrCode(qrCode.getQrCode());
    qrCodeDto.setStatus(qrCode.getStatus());
    qrCodeDto.setCreatedDate(qrCode.getCreatedDate());
    qrCodeDto.setLastEdited(qrCode.getLastEdited());
    qrCodeDto.setImageUrl(qrCode.getImageUrl());
    qrCodeDto.setActivityId(qrCode.getActivityId());
    qrCodeDto.setActivityName(qrCode.getActivity().getName());

    return qrCodeDto;
}

    public  static QRCode toEntity(QRCodeDto qrCodeDto) {
        if (qrCodeDto == null) {
            return null;
        }
        QRCode qrCode = new QRCode();
        qrCode.setId(qrCodeDto.getId());
        qrCode.setName(qrCodeDto.getName());
        qrCode.setQrCode(qrCodeDto.getQrCode());
        qrCode.setStatus(qrCodeDto.getStatus());
        qrCode.setCreatedDate(qrCodeDto.getCreatedDate());
        qrCode.setLastEdited(qrCodeDto.getLastEdited());
        qrCode.setImageUrl(qrCodeDto.getImageUrl());
        qrCode.setActivityId(qrCodeDto.getActivityId());

        return qrCode;
    }
}
