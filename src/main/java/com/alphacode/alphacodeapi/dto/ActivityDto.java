package com.alphacode.alphacodeapi.dto;

import com.alphacode.alphacodeapi.enums.ActivityEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Type is required")
    private String type;

    @Type(JsonType.class)
    @NotEmpty(message = "Data is required")
    private JsonNode data;

    private Integer status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdate;

    @NotEmpty(message = "OrganizationId is required")
    private UUID organizationId;

    private String description;

    private String imageUrl;

    private UUID musicId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String organizationName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String musicName;

    @JsonProperty(value = "statusText", access = JsonProperty.Access.READ_ONLY)
    public String getStatusText() {
        return ActivityEnum.fromCode(this.status);
    }
}
