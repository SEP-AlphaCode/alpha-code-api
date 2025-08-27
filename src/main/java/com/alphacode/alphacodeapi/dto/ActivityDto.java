package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.AccountEnum;
import com.alphacode.alphacodeapi.enums.ActivityEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String name;
    private String type;
    private String data;
    private Integer status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;
    private UUID organizationId;
    private String description;
    private String imageUrl;
    private UUID musicId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String organizationName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String musicName;

    @JsonProperty("statusText")
    public String getStatusText() {
        return ActivityEnum.fromCode(this.status);
    }
}
