package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.service.QRCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/qr-codes")
@RequiredArgsConstructor
public class QRCodeController {
    private final QRCodeService qrCodeService;

    @GetMapping
    public PagedResult<QRCodeDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "status", required = false) Integer status) {
        return qrCodeService.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public QRCodeDto getById(@PathVariable Integer id) {
        return qrCodeService.getById(id);
    }

    @GetMapping("by-code/{code}")
    public QRCodeDto getByCode(@PathVariable String code) {
        return qrCodeService.getByCode(code);
    }

    @PostMapping()
    public QRCodeDto create(@RequestBody QRCodeDto requestDto) {
        QRCodeDto qrCodeDto = new QRCodeDto();
        qrCodeDto.setCode(requestDto.getCode());
        qrCodeDto.setType(requestDto.getType());
        qrCodeDto.setData(requestDto.getData());

        return qrCodeService.create(qrCodeDto);
    }


    @PutMapping("/{id}")
    public QRCodeDto update(@PathVariable Integer id, @RequestBody QRCodeDto qrCodeDto) throws JsonProcessingException{
        return qrCodeService.update(id, qrCodeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        qrCodeService.delete(id);
    }

    @PutMapping("/{id}/status")
    public QRCodeDto changeStatus(@PathVariable Integer id, @RequestParam Integer status) {
        return qrCodeService.changeStatus(id, status);
    }
}
