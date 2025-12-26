package com.example.demo.service.impl;

import com.example.demo.entity.AlertNotification;
import com.example.demo.entity.VisitLog;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;

import java.time.LocalDateTime;
import java.util.List;

public class AlertNotificationServiceImpl implements AlertNotificationService {

    private AlertNotificationRepository alertRepository;
    private VisitLogRepository visitLogRepository;

    public AlertNotificationServiceImpl() {}

    @Override
    public AlertNotification sendAlert(Long visitLogId) {

        VisitLog log = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new RuntimeException("VisitLog not found"));

        if (alertRepository.findByVisitLogId(visitLogId).isPresent()) {
            throw new IllegalArgumentException("Alert already sent");
        }

        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(log);
        alert.setSentAt(LocalDateTime.now());
        alert.setSentTo(log.getHost().getEmail());
        alert.setAlertMessage("Visitor checked in");

        AlertNotification saved = alertRepository.save(alert);

        log.setAlertSent(true);
        visitLogRepository.save(log);

        return saved;
    }

    @Override
    public AlertNotification getAlert(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }

    @Override
    public List<AlertNotification> getAllAlerts() {
        return alertRepository.findAll();
    }
}
