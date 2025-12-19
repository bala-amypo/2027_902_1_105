package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@Tag(name = "VisitLog")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    // POST /checkin/{visitorId}/{hostId} – check in
    @PostMapping("/checkin/{visitorId}/{hostId}")
    public ResponseEntity<VisitLog> checkin(@PathVariable Long visitorId,
                                            @PathVariable Long hostId,
                                            @RequestParam String purpose) {
        return ResponseEntity.ok(
                visitLogService.checkInVisitor(visitorId, hostId, purpose)
        );
    }

    // POST /checkout/{visitLogId} – check out
    @PostMapping("/checkout/{visitLogId}")
    public ResponseEntity<VisitLog> checkout(@PathVariable Long visitLogId) {
        return ResponseEntity.ok(visitLogService.checkOutVisitor(visitLogId));
    }

    // GET /active – list active visits
    @GetMapping("/active")
    public ResponseEntity<List<VisitLog>> getActiveVisits() {
        return ResponseEntity.ok(visitLogService.getActiveVisits());
    }

    // GET /{id} – get visit log
    @GetMapping("/{id}")
    public ResponseEntity<VisitLog> getVisitLog(@PathVariable Long id) {
        return ResponseEntity.ok(visitLogService.getVisitLog(id));
    }
}