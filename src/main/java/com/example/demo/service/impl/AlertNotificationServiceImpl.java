package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.model.VisitLog;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

   
    private AlertNotificationRepository alertRepository;
    private VisitLogRepository visitLogRepository;

   
    public AlertNotificationServiceImpl(AlertNotificationRepository alertRepository,
                                        VisitLogRepository visitLogRepository) {
        this.alertRepository = alertRepository;
        this.visitLogRepository = visitLogRepository;
    }

   
    public AlertNotificationServiceImpl() {
       
    }

    @Override
    public AlertNotification sendAlert(Long visitLogId) {

        if (alertRepository == null || visitLogRepository == null) {
            throw new IllegalStateException("Repositories not initialized. Make sure to inject them.");
        }

        VisitLog visitLog = Optional.ofNullable(visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new RuntimeException("VisitLog not found")))
                .orElseThrow(() -> new RuntimeException("VisitLog cannot be null"));

        // Prevent duplicate alerts
        if (alertRepository.findByVisitLogId(visitLogId).isPresent()) {
            throw new IllegalArgumentException("Alert already sent");
        }

     
        if (visitLog.getVisitor() == null) {
            throw new IllegalStateException("VisitLog must have a Visitor set");
        }
        if (visitLog.getHost() == null || visitLog.getHost().getEmail() == null) {
            throw new IllegalStateException("VisitLog must have a Host with valid email set");
        }

        // Create new alert safely
        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(visitLog);
        alert.setSentTo(visitLog.getHost().getEmail());
        alert.setAlertMessage("Visitor " + visitLog.getVisitor().getFullName() + " has checked in");
        alert.setSentAt(LocalDateTime.now());

        AlertNotification savedAlert = alertRepository.save(alert);

        // Update visit log to indicate alert sent
        visitLog.setAlertSent(true);
        visitLogRepository.save(visitLog);

        return savedAlert;
    }

    @Override
    public AlertNotification getAlert(Long id) {
        if (alertRepository == null) {
            throw new IllegalStateException("alertRepository not initialized");
        }
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }

    @Override
    public List<AlertNotification> getAllAlerts() {
        if (alertRepository == null) {
            throw new IllegalStateException("alertRepository not initialized");
        }
        return alertRepository.findAll();
    }
}
