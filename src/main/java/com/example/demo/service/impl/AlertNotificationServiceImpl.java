package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    @Autowired
    private AlertNotificationRepository alertNotificationRepository;

    // CREATE ALERT
    @Override
    public AlertNotification createAlert(Long visitLogId, String message) {

        AlertNotification alert = new AlertNotification();
        alert.setMessage(message);
        alert.setAlertTime(LocalDateTime.now());

        return alertNotificationRepository.save(alert);
    }

    // GET ALL ALERTS
    @Override
    public List<AlertNotification> getAllAlerts() {
        return alertNotificationRepository.findAll();
    }

    // GET SINGLE ALERT
    @Override
    public AlertNotification getAlert(Long alertId) {
        return alertNotificationRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found with id: " + alertId));
    }
}
