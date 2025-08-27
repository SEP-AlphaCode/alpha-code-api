package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.entity.QRCode;

public class QRCodeMapper {

    public static QRCodeDto toDto(QRCode qrCode) {
        if (qrCode == null) return null;

        QRCodeDto dto = new QRCodeDto();
        dto.setId(qrCode.getId());
        dto.setName(qrCode.getName());
        dto.setQrCode(qrCode.getQrCode());
        dto.setStatus(qrCode.getStatus());
        dto.setCreatedDate(qrCode.getCreatedDate());
        dto.setLastEdited(qrCode.getLastEdited());
        dto.setImageUrl(qrCode.getImageUrl());
        dto.setActivityId(qrCode.getActivityId());
        dto.setAccountId(qrCode.getAccountId());
        dto.setActivityName(qrCode.getActivity() != null ? qrCode.getActivity().getName() : null);

        return dto;
    }

    public static QRCode toEntity(QRCodeDto dto) {
        if (dto == null) return null;

        QRCode entity = new QRCode();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setQrCode(dto.getQrCode());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastEdited(dto.getLastEdited());
        entity.setImageUrl(dto.getImageUrl());
        entity.setActivityId(dto.getActivityId());
        entity.setAccountId(dto.getAccountId());

        return entity;
    }
}
