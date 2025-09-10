package com.alphacode.alphacodeapi.controller;

import com.alphacode.alphacodeapi.dto.ActivityDto;
import com.alphacode.alphacodeapi.service.DanceService;
import com.alphacode.alphacodeapi.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/online-users")
    @Operation(summary = "Get count of online users")
    public Long countOnlineUsers() {
        return service.countOnlineUsers();
    }

    @GetMapping("/stats/{roleName}")
    @Operation(summary = "Get user stats by role (Teacher, Admin, etc.)")
    public Map<String, Object> getStatsByRole(@PathVariable String roleName) {
        Map<String, Object> result = new HashMap<>();
        result.put("role", roleName);
        result.put("total", service.countUsersByRole(roleName));
        result.put("newThisMonth", service.countNewUsersByRoleThisMonth(roleName));
        result.put("growthRate", service.calculateGrowthRateByRole(roleName));
        return result;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get summary stats")
    public ResponseEntity<Map<String, Long>> getSummaryStats() {
        return ResponseEntity.ok(service.getSummaryStats());
    }

    @GetMapping("/top-activity/today")
    public List<ActivityDto> getTopToday(@RequestParam(defaultValue = "5") int limit) {
        return service.getTopActivitiesToday(limit);
    }

    @GetMapping("/top-activity/week")
    public List<ActivityDto> getTopWeek(@RequestParam(defaultValue = "5") int limit) {
        return service.getTopActivitiesThisWeek(limit);
    }

    @GetMapping("/top-activity/month")
    public List<ActivityDto> getTopMonth(@RequestParam(defaultValue = "5") int limit) {
        return service.getTopActivitiesThisMonth(limit);
    }
}
