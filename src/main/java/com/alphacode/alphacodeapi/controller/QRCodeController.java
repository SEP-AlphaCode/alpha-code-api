package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public QRCodeDto create(@RequestBody QRCodeDto qrCodeDto) {
        return qrCodeService.create(qrCodeDto);
    }

    @PutMapping("/{id}")
    public QRCodeDto update(@PathVariable Integer id, @RequestBody QRCodeDto qrCodeDto) {
        return qrCodeService.update(id, qrCodeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        qrCodeService.delete(id);
    }
}
