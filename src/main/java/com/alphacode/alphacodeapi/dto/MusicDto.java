package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.MusicEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private String url;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;
    private Integer duration;
    private Integer status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID classId;
    private String image;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return MusicEnum.fromCode(this.status);
    }
}
