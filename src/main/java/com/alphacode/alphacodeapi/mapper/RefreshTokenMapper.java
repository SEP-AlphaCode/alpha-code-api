package com.alphacode.alphacodeapi.mapper;

import com.alphacode.alphacodeapi.dto.RefreshTokenDto;
import com.alphacode.alphacodeapi.entity.RefreshToken;

public class RefreshTokenMapper {

    public static RefreshTokenDto toDto(RefreshToken entity) {
        if (entity == null) {
            return null;
        }
        RefreshTokenDto dto = new RefreshTokenDto();
        dto.setId(entity.getId());
        dto.setToken(entity.getToken());        // client cần token để gửi lên
        dto.setAccountId(entity.getAccountId()); // READ_ONLY cho admin/debug
        dto.setExpiredAt(entity.getExpiredAt());
        dto.setCreateAt(entity.getCreateAt());
        dto.setRevokedAt(entity.getRevokedAt());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }

    public static RefreshToken toEntity(RefreshTokenDto dto) {
        if (dto == null) {
            return null;
        }
        RefreshToken entity = new RefreshToken();
        entity.setId(dto.getId());
        entity.setToken(dto.getToken());         // nhận từ client khi refresh
        entity.setAccountId(dto.getAccountId()); // thường server set, client không cần
        entity.setExpiredAt(dto.getExpiredAt()); // server set khi tạo
        entity.setCreateAt(dto.getCreateAt());   // server set
        entity.setRevokedAt(dto.getRevokedAt()); // server set khi revoke
        entity.setIsActive(dto.getIsActive());   // server set
        return entity;
    }
}
