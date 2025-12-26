package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.AlertNotification;
import com.example.demo.service.AlertNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alerts", description = "Alert notifications to hosts")
@SecurityRequirement(name = "Bearer Authentication")
public class AlertNotificationController {

    private final AlertNotificationService alertNotificationService;

    public AlertNotificationController(AlertNotificationService alertNotificationService) {
        this.alertNotificationService = alertNotificationService;
    }

    @PostMapping("/send/{visitLogId}")
    @Operation(summary = "Send alert notification for a visit")
    public ResponseEntity<ApiResponse<AlertNotification>> sendAlert(@PathVariable Long visitLogId) {
        AlertNotification alert = alertNotificationService.sendAlert(visitLogId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Alert sent successfully", alert));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID")
    public ResponseEntity<ApiResponse<AlertNotification>> getAlert(@PathVariable Long id) {
        AlertNotification alert = alertNotificationService.getAlert(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Alert retrieved successfully", alert));
    }

    @GetMapping
    @Operation(summary = "Get all alerts")
    public ResponseEntity<ApiResponse<List<AlertNotification>>> getAllAlerts() {
        List<AlertNotification> alerts = alertNotificationService.getAllAlerts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Alerts retrieved successfully", alerts));
    }
}