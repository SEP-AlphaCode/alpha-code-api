package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ClassDto;
import com.alphacode.alphacodeapi.dto.PagedResult;

import java.util.UUID;

public interface ClassService {

    /**
     * Lấy tất cả class có phân trang
     *
     * @param page   số trang (bắt đầu từ 1)
     * @param size   số phần tử mỗi trang
     * @param status lọc theo trạng thái (có thể null)
     * @return danh sách class
     */
    PagedResult<ClassDto> getAll(int page, int size, Integer status);

    /**
     * Lấy chi tiết class theo ID
     */
    ClassDto getById(UUID id);

    /**
     * Tạo mới class
     */
    ClassDto create(ClassDto dto);

    /**
     * Update toàn bộ class (PUT)
     */
    ClassDto update(UUID id, ClassDto dto);

    /**
     * Update một phần class (PATCH)
     */
    ClassDto patchUpdate(UUID id, ClassDto dto);

    /**
     * Soft delete class (chỉ đổi status = 0)
     */
    String delete(UUID id);

    /**
     * Thay đổi trạng thái class
     */
    ClassDto changeClassStatus(UUID id, Integer status);
}
