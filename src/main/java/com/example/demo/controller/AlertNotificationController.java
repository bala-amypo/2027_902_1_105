package com.example.demo.controller;

import com.example.demo.dto.AlertNotificationDTO;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.AlertNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Alert Notifications")
@RestController
@RequestMapping("/api/alerts")
@SecurityRequirement(name = "bearer")
public class AlertNotificationController {

    @Autowired
    private AlertNotificationService alertNotificationService;

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID")
    public ResponseEntity<ApiResponse> getAlert(
            @Parameter(description = "Alert ID") @PathVariable Long id) {
        AlertNotificationDTO alert = alertNotificationService.getAlert(id);
        return ResponseEntity.ok(new ApiResponse(true, "Alert found", alert));
    }
}
