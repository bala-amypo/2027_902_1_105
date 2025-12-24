package com.example.demo.controller;

import com.example.demo.dto.AlertNotificationDTO;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.AlertNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alerts", description = "Alert notifications to hosts")
@RestController
@RequestMapping("/api/alerts")
@SecurityRequirement(name = "bearerAuth")
public class AlertNotificationController {

    private final AlertNotificationService alertNotificationService;

    public AlertNotificationController(AlertNotificationService alertNotificationService) {
        this.alertNotificationService = alertNotificationService;
    }

    @Operation(summary = "Send alert for visit log")
    @PostMapping("/send/{visitLogId}")
    public ResponseEntity<ApiResponse> sendAlert(
            @Parameter(description = "Visit Log ID") @PathVariable Long visitLogId) {
        AlertNotificationDTO alert = alertNotificationService.sendAlert(visitLogId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Alert sent successfully", alert));
    }

    @Operation(summary = "Get all alerts")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAlerts() {
        List<AlertNotificationDTO> alerts = alertNotificationService.getAllAlerts();
        return ResponseEntity.ok(new ApiResponse(true, "Alerts retrieved", alerts));
    }

    @Operation(summary = "Get alert by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAlert(
            @Parameter(description = "Alert ID") @PathVariable Long id) {
        AlertNotificationDTO alert = alertNotificationService.getAlert(id);
        return ResponseEntity.ok(new ApiResponse(true, "Alert found", alert));
    }
}
