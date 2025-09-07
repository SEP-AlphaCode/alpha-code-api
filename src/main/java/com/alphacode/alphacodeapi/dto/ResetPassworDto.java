package com.alphacode.alphacodeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassworDto implements Serializable {
    private String resetToken;
    private String newPassword;
}
