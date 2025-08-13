package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface QRCodeService {
    PagedResult<QRCodeDto> getAll(int page, int size, Integer status);

    QRCodeDto getById(UUID id);

    QRCodeDto create(QRCodeDto qrCodeDto);

    QRCodeDto update(UUID id,QRCodeDto qrCodeDto) throws JsonProcessingException;

    void delete(UUID id);

    QRCodeDto getByCode(String code);

    QRCodeDto changeStatus(UUID id, Integer status);
}
