package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.service.QRCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/qr-codes")
@RequiredArgsConstructor
@Tag(name = "QRCodes")
public class QRCodeController {
    private final QRCodeService qrCodeService;

    @GetMapping
    public PagedResult<QRCodeDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "status", required = false) Integer status) {
        return qrCodeService.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    public QRCodeDto getById(@PathVariable UUID id) {
        return qrCodeService.getById(id);
    }

    @GetMapping("by-code/{code}")
    public QRCodeDto getByCode(@PathVariable String code) {
        return qrCodeService.getByCode(code);
    }

    @PostMapping()
    public QRCodeDto create(@RequestBody QRCodeDto requestDto) {
        QRCodeDto qrCodeDto = new QRCodeDto();
        qrCodeDto.setName(requestDto.getName());
        qrCodeDto.setQrCode(requestDto.getQrCode());
        qrCodeDto.setActivityId(requestDto.getActivityId());
        qrCodeDto.setAccountId(requestDto.getAccountId());

        return qrCodeService.create(qrCodeDto);
    }


    @PutMapping("/{id}")
    public QRCodeDto update(@PathVariable UUID id, @RequestBody QRCodeDto qrCodeDto) throws JsonProcessingException{
        return qrCodeService.update(id, qrCodeDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch update QRCode")
    public QRCodeDto patchUpdate(@PathVariable UUID id, @RequestBody QRCodeDto qrCodeDto) {
        return qrCodeService.patchUpdate(id, qrCodeDto);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update QRCode status")
    public QRCodeDto updateStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return qrCodeService.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        return qrCodeService.delete(id);
    }

    @PutMapping("/{id}/status")
    public QRCodeDto changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return qrCodeService.changeStatus(id, status);
    }
}
