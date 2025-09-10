package com.alphacode.alphacodeapi.service;

import com.alphacode.alphacodeapi.dto.ActivityDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DashboardService {
    long countOnlineUsers();

    void addOnlineUser(UUID accountId);

    void removeOnlineUser(UUID accountId);

    long countUsersByRole(String roleName);

    long countNewUsersByRoleThisMonth(String roleName);

    double calculateGrowthRateByRole(String roleName);

    Map<String, Object> getUserStats();

    Map<String, Long> getSummaryStats();

    Map<String, Long> getExtensionStats();

    List<ActivityDto> getTopActivitiesToday(int topN);

    List<ActivityDto> getTopActivitiesThisWeek(int topN);

    List<ActivityDto> getTopActivitiesThisMonth(int topN);
}
