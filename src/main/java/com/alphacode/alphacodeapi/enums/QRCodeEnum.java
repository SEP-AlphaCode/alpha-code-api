package com.alphacode.alphacodeapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QRCodeEnum {
    DELETED(0, "DELETED"),
    ACTIVE(1, "ACTIVE"),
    DISABLED(2, "DISABLED");

    private final int code;
    private final String description;

    public static String fromCode(Integer code) {
        if (code == null) return null;
        for (QRCodeEnum s : values()) {
            if (s.code == code) {
                return s.description;
            }
        }
        return "UNDEFINED";
    }
}
