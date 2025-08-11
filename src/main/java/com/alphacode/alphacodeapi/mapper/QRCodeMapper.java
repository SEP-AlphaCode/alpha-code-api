package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;

public class QRCodeMapper {
    public static QRCodeDto toDto(com.alphacode.alphacodeapi.entity.QRCode qrCode) {
        if (qrCode == null) {
            return null;
        }
        QRCodeDto qrCodeDto = new QRCodeDto();
        qrCodeDto.setId(qrCode.getId());
        qrCodeDto.setCode(qrCode.getCode());
        qrCodeDto.setAction(qrCode.getAction());
        qrCodeDto.setExpression(qrCode.getExpression());
        qrCodeDto.setVoice(qrCode.getVoice());
        qrCodeDto.setStatus(qrCode.getStatus());
        qrCodeDto.setCreatedDate(qrCode.getCreatedDate());
        qrCodeDto.setLastEdited(qrCode.getLastEdited());
        qrCodeDto.setImageUrl(qrCode.getImageUrl());

        return qrCodeDto;
    }

    public  static QRCode toEntity(QRCodeDto qrCodeDto) {
        if (qrCodeDto == null) {
            return null;
        }
        QRCode qrCode = new QRCode();
        qrCode.setId(qrCodeDto.getId());
        qrCode.setCode(qrCodeDto.getCode());
        qrCode.setAction(qrCodeDto.getAction());
        qrCode.setExpression(qrCodeDto.getExpression());
        qrCode.setVoice(qrCodeDto.getVoice());
        qrCode.setStatus(qrCodeDto.getStatus());
        qrCode.setCreatedDate(qrCodeDto.getCreatedDate());
        qrCode.setLastEdited(qrCodeDto.getLastEdited());
        qrCode.setImageUrl(qrCodeDto.getImageUrl());

        return qrCode;
    }
}
