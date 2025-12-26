package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.model.VisitLog;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    @Autowired
    private AlertNotificationRepository alertRepo;

    @Autowired
    private VisitLogRepository visitLogRepo;

    @Override
    public AlertNotification createAlert(VisitLog log, String message) {
        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(log);
        alert.setAlertMessage(message);
        alert.setSentAt(LocalDateTime.now());
        alert.setSentTo(log.getVisitor().getEmail());

        // Mark VisitLog as alert sent
        log.setAlertSent(true);
        visitLogRepo.save(log);

        return alertRepo.save(alert);
    }
}
