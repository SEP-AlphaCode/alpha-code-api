package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.repository.*;
import com.alphacode.alphacodeapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final RedisTemplate<String, String> redisTemplate;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final RobotRepository robotRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private static final String ONLINE_KEY_PREFIX = "online:user:";

    @Override
    public long countOnlineUsers() {
        Set<String> keys = redisTemplate.keys(ONLINE_KEY_PREFIX + "*");
        return keys != null ? keys.size() : 0;
    }

    @Override
    public void addOnlineUser(UUID accountId) {
        String key = ONLINE_KEY_PREFIX + accountId;
        redisTemplate.opsForValue().set(key, "online", 5, TimeUnit.MINUTES);
    }

    @Override
    public void removeOnlineUser(UUID accountId) {
        String key = ONLINE_KEY_PREFIX + accountId;
        redisTemplate.delete(key);
    }

    @Override
    public long countUsersByRole(String roleName) {
        UUID roleId = getRoleIdByName(roleName);
        return accountRepository.countByRoleId(roleId);
    }

    @Override
    public long countNewUsersByRoleThisMonth(String roleName) {
        UUID roleId = getRoleIdByName(roleName);
        LocalDate now = LocalDate.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = now.plusMonths(1).withDayOfMonth(1).atStartOfDay();
        return accountRepository.countByRoleIdAndCreatedDateBetween(roleId, startOfMonth, endOfMonth);
    }

    @Override
    public double calculateGrowthRateByRole(String roleName) {
        UUID roleId = getRoleIdByName(roleName);
        LocalDate now = LocalDate.now();

        // tháng này
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfThisMonth = now.plusMonths(1).withDayOfMonth(1).atStartOfDay();
        long newThisMonth = accountRepository.countByRoleIdAndCreatedDateBetween(roleId, startOfThisMonth, endOfThisMonth);

        // tháng trước
        LocalDateTime startOfLastMonth = now.minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfLastMonth = startOfThisMonth;
        long newLastMonth = accountRepository.countByRoleIdAndCreatedDateBetween(roleId, startOfLastMonth, endOfLastMonth);

        if (newLastMonth == 0) {
            return newThisMonth > 0 ? 100.0 : 0.0;
        }

        return ((double) (newThisMonth - newLastMonth) / newLastMonth) * 100.0;
    }

    @Override
    @Cacheable(value = "dashboardSummary", key = "'summary'")
    public Map<String, Long> getSummaryStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalAccounts", accountRepository.count());
        stats.put("totalActivities", activityRepository.count());
        stats.put("totalRobots", robotRepository.count());
        stats.put("totalOrganizations", organizationRepository.count());
        return stats;
    }

    private UUID getRoleIdByName(String roleName) {
        return roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName))
                .getId();
    }
}