package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit-logs")
public class VisitLogController {

    @Autowired
    private VisitLogService visitLogService;

    @PostMapping("/checkin")
    public ResponseEntity<VisitLog> checkIn(
            @RequestParam Long visitorId,
            @RequestParam Long hostId,
            @RequestParam String purpose) {

        return ResponseEntity.ok(
                visitLogService.checkInVisitor(visitorId, hostId, purpose)
        );
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<VisitLog> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(
                visitLogService.checkOutVisitor(id)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitLog> getVisitLog(@PathVariable Long id) {
        return ResponseEntity.ok(visitLogService.getVisitLog(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<VisitLog>> getActiveVisits() {
        return ResponseEntity.ok(visitLogService.getActiveVisits());
    }
}