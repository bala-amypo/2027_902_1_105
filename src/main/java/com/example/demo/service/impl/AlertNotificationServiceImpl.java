package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.model.VisitLog;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    @Autowired
    private AlertNotificationRepository alertNotificationRepository;

    @Autowired
    private VisitLogRepository visitLogRepository;

    @Override
    public AlertNotification createAlert(Long visitLogId, String message) {
        VisitLog log = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new RuntimeException("VisitLog not found with ID: " + visitLogId));

        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(log);
        alert.setMessage(message);            // updated from setAlertMessage
        alert.setAlertTime(LocalDateTime.now());

        return alertNotificationRepository.save(alert);
    }

    @Override
    public List<AlertNotification> getAllAlerts() {
        return alertNotificationRepository.findAll();
    }

    @Override
    public List<AlertNotification> getAlertsByVisitLog(Long visitLogId) {
        VisitLog log = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new RuntimeException("VisitLog not found with ID: " + visitLogId));
        return alertNotificationRepository.findByVisitLog(log);
    }
}
