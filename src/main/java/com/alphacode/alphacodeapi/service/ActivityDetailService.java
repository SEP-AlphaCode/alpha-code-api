package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActivityDetailDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

public interface ActivityDetailService {
    PagedResult<ActivityDetailDto> getAll(int page, int size, String type);

    ActivityDetailDto getById(Integer id);

    ActivityDetailDto create(ActivityDetailDto dto);

    ActivityDetailDto update(Integer id, ActivityDetailDto dto);

    ActivityDetailDto patchUpdate(Integer id, ActivityDetailDto dto);

    String delete(Integer id);
}
