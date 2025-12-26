package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.VisitLog;
import com.example.demo.service.VisitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@Tag(name = "Visit Logs", description = "Check-in and check-out operations")
@SecurityRequirement(name = "Bearer Authentication")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    @PostMapping("/checkin/{visitorId}/{hostId}")
    @Operation(summary = "Check in a visitor")
    public ResponseEntity<ApiResponse<VisitLog>> checkInVisitor(
            @PathVariable Long visitorId,
            @PathVariable Long hostId,
            @RequestBody String purpose) {
        VisitLog visitLog = visitLogService.checkInVisitor(visitorId, hostId, purpose);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Visitor checked in successfully", visitLog));
    }

    @PostMapping("/checkout/{visitLogId}")
    @Operation(summary = "Check out a visitor")
    public ResponseEntity<ApiResponse<VisitLog>> checkOutVisitor(@PathVariable Long visitLogId) {
        VisitLog visitLog = visitLogService.checkOutVisitor(visitLogId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Visitor checked out successfully", visitLog));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active visits")
    public ResponseEntity<ApiResponse<List<VisitLog>>> getActiveVisits() {
        List<VisitLog> activeVisits = visitLogService.getActiveVisits();
        return ResponseEntity.ok(new ApiResponse<>(true, "Active visits retrieved successfully", activeVisits));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get visit log by ID")
    public ResponseEntity<ApiResponse<VisitLog>> getVisitLog(@PathVariable Long id) {
        VisitLog visitLog = visitLogService.getVisitLog(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Visit log retrieved successfully", visitLog));
    }
}