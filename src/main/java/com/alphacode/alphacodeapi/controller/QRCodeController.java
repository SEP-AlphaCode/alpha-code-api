package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.PagedResult;
import com.alphacode.alphacodeapi.dto.QRCodeDto;
import com.alphacode.alphacodeapi.service.QRCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/qr-codes")
@RequiredArgsConstructor
@Tag(name = "QRCodes")
public class QRCodeController {
    private final QRCodeService qrCodeService;

    @GetMapping
    @Operation(summary = "Get all QR codes with pagination and optional status filter")
    public PagedResult<QRCodeDto> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "status", required = false) Integer status) {
        return qrCodeService.getAll(page, size, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get QR code by id")
    public QRCodeDto getById(@PathVariable UUID id) {
        return qrCodeService.getById(id);
    }

    @GetMapping("by-code/{code}")
    @Operation(summary = "Get QR code by code")
    public QRCodeDto getByCode(@PathVariable String code) {
        return qrCodeService.getByCode(code);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Create new QR code")
    public QRCodeDto create(@Valid @RequestBody QRCodeDto requestDto) {
//        QRCodeDto qrCodeDto = new QRCodeDto();
//        qrCodeDto.setName(requestDto.getName());
//        qrCodeDto.setQrCode(requestDto.getQrCode());
//        qrCodeDto.setActivityId(requestDto.getActivityId());
//        qrCodeDto.setAccountId(requestDto.getAccountId());
//
//        return qrCodeService.create(qrCodeDto);
        return qrCodeService.create(requestDto);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update QRCode")
    public QRCodeDto update(@PathVariable UUID id, @Valid @RequestBody QRCodeDto qrCodeDto) throws JsonProcessingException {
        return qrCodeService.update(id, qrCodeDto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Patch update QRCode")
    public QRCodeDto patchUpdate(@PathVariable UUID id, @Valid @RequestBody QRCodeDto qrCodeDto) {
        return qrCodeService.patchUpdate(id, qrCodeDto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Update QRCode status")
    public QRCodeDto updateStatus(@PathVariable UUID id, @Valid @RequestParam Integer status) {
        return qrCodeService.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Delete QRCode by id")
    public String delete(@PathVariable UUID id) {
        return qrCodeService.delete(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Teacher')")
    @Operation(summary = "Change QRCode status")
    public QRCodeDto changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return qrCodeService.changeStatus(id, status);
    }
}
