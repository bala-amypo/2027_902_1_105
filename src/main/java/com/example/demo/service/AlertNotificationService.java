package com.example.demo.service;

import com.example.demo.entity.AlertNotification;
import java.util.List;

public interface AlertNotificationService {
    AlertNotification sendAlert(Long visitLogId);
    AlertNotification getAlert(Long id);
    List<AlertNotification> getAllAlerts();
}