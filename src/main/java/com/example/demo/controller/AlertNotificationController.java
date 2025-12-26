package com.example.demo.controller;

import com.example.demo.model.AlertNotification;
import com.example.demo.service.AlertNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertNotificationController {

    @Autowired
    private AlertNotificationService alertService;

    @PostMapping("/{visitLogId}")
    public ResponseEntity<AlertNotification> sendAlert(@PathVariable Long visitLogId) {
        return ResponseEntity.ok(alertService.sendAlert(visitLogId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertNotification> getAlert(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlert(id));
    }

    @GetMapping
    public ResponseEntity<List<AlertNotification>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }
}
