package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.DanceEnum;
import com.alphacode.alphacodeapi.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer status;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return RoleEnum.fromCode(this.status);
    }
}
