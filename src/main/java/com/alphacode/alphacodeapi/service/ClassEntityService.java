package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ClassEntityDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ClassEntityService {

    /**
     * Lấy tất cả class có phân trang
     *
     * @param page   số trang (bắt đầu từ 1)
     * @param size   số phần tử mỗi trang
     * @param status lọc theo trạng thái (có thể null)
     * @return danh sách class
     */
    PagedResult<ClassEntityDto> getAll(int page, int size, Integer status);

    /**
     * Lấy chi tiết class theo ID
     */
    ClassEntityDto getById(UUID id);

    /**
     * Tạo mới class
     */
    ClassEntityDto create(ClassEntityDto dto);

    /**
     * Update toàn bộ class (PUT)
     */
    ClassEntityDto update(UUID id, ClassEntityDto dto);

    /**
     * Update một phần class (PATCH)
     */
    ClassEntityDto patchUpdate(UUID id, ClassEntityDto dto);

    /**
     * Soft delete class (chỉ đổi status = 0)
     */
    String delete(UUID id);
}
