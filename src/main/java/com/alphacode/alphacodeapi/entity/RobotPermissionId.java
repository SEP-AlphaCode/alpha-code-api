package com.alphacode.alphacodeapi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RobotPermissionId implements Serializable {
    private UUID robotId;
    private UUID activityId;
}