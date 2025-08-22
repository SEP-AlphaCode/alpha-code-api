package com.alphacode.alphacodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassworDto {
    private String resetToken;
    private String newPassword;
}
