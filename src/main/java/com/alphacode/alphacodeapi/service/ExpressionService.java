package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ExpressionDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ExpressionService {
    PagedResult<ExpressionDto> getAll(int page, int size, Integer status);

    ExpressionDto getById(UUID id);

    ExpressionDto create(ExpressionDto dto);

    ExpressionDto update(UUID id, ExpressionDto dto);

    ExpressionDto patchUpdate(UUID id, ExpressionDto dto);

    String delete(UUID id);

    ExpressionDto changeExpressionStatus(UUID id, Integer status);
}