package com.example.apiproject.service;

import com.example.apiproject.dto.AlertNotificationDTO;
import java.util.List;

public interface AlertNotificationService {
    AlertNotificationDTO sendAlert(Long visitLogId);
    AlertNotificationDTO getAlert(Long id);
    List<AlertNotificationDTO> getAllAlerts();
}