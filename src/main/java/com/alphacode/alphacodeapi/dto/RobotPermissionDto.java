package com.alphacode.alphacodeapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RobotPermissionDto {
    private UUID id;

    private UUID robotId;

    private UUID activityId;

    private Integer status;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;


}
