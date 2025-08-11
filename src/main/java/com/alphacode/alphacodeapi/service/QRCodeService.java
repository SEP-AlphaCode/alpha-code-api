package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;

import java.util.List;

public interface QRCodeService {
    PagedResult<QRCodeDto> getAll(int page, int size, Integer status);

    QRCodeDto getById(Integer id);

    QRCodeDto create(QRCodeDto qrCodeDto);

    QRCodeDto update(Integer id,QRCodeDto qrCodeDto);

    void delete(Integer id);
}
