package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.model.VisitLog;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    private final AlertNotificationRepository alertRepository;
    private final VisitLogRepository visitLogRepository;

    // Constructor injection for Spring + unit tests
    public AlertNotificationServiceImpl(AlertNotificationRepository alertRepository,
                                        VisitLogRepository visitLogRepository) {
        this.alertRepository = alertRepository;
        this.visitLogRepository = visitLogRepository;
    }

    @Override
    public AlertNotification sendAlert(Long visitLogId) {
        // Fetch VisitLog or throw exception
        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("VisitLog not found"));

        // Prevent duplicate alerts
        Optional<AlertNotification> existingAlert = alertRepository.findByVisitLogId(visitLogId);
        if (existingAlert.isPresent()) {
            throw new IllegalArgumentException("Alert already sent");
        }

        // Safe null-check for visitor and host
        if (visitLog.getVisitor() == null || visitLog.getHost() == null || visitLog.getHost().getEmail() == null) {
            throw new IllegalStateException("Visitor or Host information is missing");
        }

        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(visitLog);
        alert.setSentTo(visitLog.getHost().getEmail());
        alert.setAlertMessage("Visitor " + visitLog.getVisitor().getFullName() + " has checked in");
        alert.setSentAt(LocalDateTime.now());

        AlertNotification savedAlert = alertRepository.save(alert);

        // Mark alert sent in VisitLog
        visitLog.setAlertSent(true);
        visitLogRepository.save(visitLog);

        return savedAlert;
    }

    @Override
    public AlertNotification getAlert(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
    }

    @Override
    public List<AlertNotification> getAllAlerts() {
        return alertRepository.findAll();
    }
}
