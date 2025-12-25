package com.example.demo.controller;

import com.example.demo.dto.VisitLogDTO;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.VisitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Visit Logs", description = "Check-in and check-out operations")
@RestController
@RequestMapping("/api/visits")
@SecurityRequirement(name = "bearerAuth")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    @Operation(summary = "Check-in visitor")
    @PostMapping("/checkin/{visitorId}/{hostId}")
    public ResponseEntity<ApiResponse> checkInVisitor(
            @Parameter(description = "Visitor ID") @PathVariable Long visitorId,
            @Parameter(description = "Host ID") @PathVariable Long hostId,
            @RequestBody Map<String, String> request) {
        String purpose = request.get("purpose");
        VisitLogDTO visitLog = visitLogService.checkInVisitor(visitorId, hostId, purpose);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Visitor checked in", visitLog));
    }

    @Operation(summary = "Check-out visitor")
    @PostMapping("/checkout/{visitLogId}")
    public ResponseEntity<ApiResponse> checkOutVisitor(
            @Parameter(description = "Visit Log ID") @PathVariable Long visitLogId) {
        VisitLogDTO visitLog = visitLogService.checkOutVisitor(visitLogId);
        return ResponseEntity.ok(new ApiResponse(true, "Visitor checked out", visitLog));
    }

    @Operation(summary = "Get active visits")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveVisits() {
        List<VisitLogDTO> activeVisits = visitLogService.getActiveVisits();
        return ResponseEntity.ok(new ApiResponse(true, "Active visits", activeVisits));
    }

    @Operation(summary = "Get visit log by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVisitLog(
            @Parameter(description = "Visit Log ID") @PathVariable Long id) {
        VisitLogDTO visitLog = visitLogService.getVisitLog(id);
        return ResponseEntity.ok(new ApiResponse(true, "Visit log found", visitLog));
    }
}
