package com.alphacode.alphacodeapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Activity entity.
 * Contains request and response fields with appropriate validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDto {
    // Response fields (read-only)
    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;

    // Request fields (for create/update)
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Data is required")
    private String data;

    @NotNull(message = "Status is required")
    private Integer status;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    // Foreign key fields
    @NotNull(message = "Organization ID is required")
    @JsonProperty("organizationId")
    private UUID organizationId;

    @JsonProperty("musicId")
    private UUID musicId;

    // Response-only fields (ignored on deserialization)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String organizationName;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String musicName;

    /**
     * Factory method to create a response DTO with all fields
     */
    public static ActivityDto withAllFields(
            UUID id, String name, String type, String data, Integer status,
            LocalDateTime createDate, LocalDateTime lastUpdate, UUID organizationId,
            String description, String imageUrl, UUID musicId,
            String organizationName, String musicName) {
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
