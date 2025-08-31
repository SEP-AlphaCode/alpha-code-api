package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ActivityService {
    PagedResult<ActivityDto> getAll(int page, int size, Integer status);
    ActivityDto getById(UUID id);
    ActivityDto create(ActivityDto activityDto);
    ActivityDto update(UUID id, ActivityDto activityDto);
    ActivityDto patchUpdate(UUID id, ActivityDto activityDto);
    String delete(UUID id);
    ActivityDto getByQRCode(String qrCode);
}
