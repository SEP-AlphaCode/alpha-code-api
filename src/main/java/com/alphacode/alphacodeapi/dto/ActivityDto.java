package com.alphacode.alphacodeapi.dto;

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
    private List<Map<String, Object>> data;
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

    public static ActivityDto withAllFields(UUID id, String name, String type, List<Map<String, Object>> data,
                                            Integer status, LocalDateTime createDate, LocalDateTime lastUpdate,
                                            UUID organizationId, String description, String imageUrl,
                                            UUID musicId, String organizationName, String musicName) {
        return ActivityDto.builder()
                .id(id)
                .name(name)
                .type(type)
                .data(data)
                .status(status)
                .createDate(createDate)
                .lastUpdate(lastUpdate)
                .organizationId(organizationId)
                .description(description)
                .imageUrl(imageUrl)
                .musicId(musicId)
                .organizationName(organizationName)
                .musicName(musicName)
                .build();
    }
}
