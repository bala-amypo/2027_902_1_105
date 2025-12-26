package com.example.demo.service;

import com.example.demo.model.AlertNotification;
import java.util.List;

public interface AlertNotificationService {

    AlertNotification createAlert(Long visitLogId, String message);

    List<AlertNotification> getAllAlerts();

    List<AlertNotification> getAlertsByVisitLog(Long visitLogId);
}
