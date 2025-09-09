package com.alphacode.alphacodeapi.service;

import java.util.Map;
import java.util.UUID;

public interface DashboardService {
    long countOnlineUsers();

    void addOnlineUser(UUID accountId);

    void removeOnlineUser(UUID accountId);

    long countUsersByRole(String roleName);

    long countNewUsersByRoleThisMonth(String roleName);

    double calculateGrowthRateByRole(String roleName);

    Map<String, Long> getSummaryStats();
}
