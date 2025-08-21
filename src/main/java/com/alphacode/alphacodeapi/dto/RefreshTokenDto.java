package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;   // server quản lý, client không set

    private String token; // client sẽ nhận được khi login/refresh

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID accountId; // không cho client gửi, chỉ để admin hoặc backend đọc

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime expiredAt; // client cần biết để refresh

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createAt;  // server quản lý

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime revokedAt; // server quản lý

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive; // server quản lý
}
