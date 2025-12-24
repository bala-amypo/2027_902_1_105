package com.example.apiproject.service.impl;

import com.example.apiproject.dto.AlertNotificationDTO;
import com.example.apiproject.model.AlertNotification;
import com.example.apiproject.model.Host;
import com.example.apiproject.model.VisitLog;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.AlertNotificationRepository;
import com.example.apiproject.repository.VisitLogRepository;
import com.example.apiproject.service.AlertNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    private final AlertNotificationRepository alertRepository;
    private final VisitLogRepository visitLogRepository;

    public AlertNotificationServiceImpl(AlertNotificationRepository alertRepository,
                                        VisitLogRepository visitLogRepository) {
        this.alertRepository = alertRepository;
        this.visitLogRepository = visitLogRepository;
    }

    @Override
    public AlertNotificationDTO sendAlert(Long visitLogId) {
        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));

        if (alertRepository.findByVisitLogId(visitLogId).isPresent()) {
            throw new IllegalArgumentException("Alert already sent");
        }

        Host host = visitLog.getHost();
        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(visitLog);
        alert.setSentTo(host.getEmail());
        alert.setAlertMessage("Visitor " + visitLog.getVisitor().getFullName() + 
                             " has checked in for visit purpose: " + visitLog.getPurpose());

        AlertNotification saved = alertRepository.save(alert);
        return modelToDto(saved);
    }

    @Override
    public AlertNotificationDTO getAlert(Long id) {
        AlertNotification alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
        return modelToDto(alert);
    }

    @Override
    public List<AlertNotificationDTO> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private AlertNotificationDTO modelToDto(AlertNotification alert) {
        return new AlertNotificationDTO(
                alert.getId(),
                alert.getVisitLog().getId(),
                alert.getSentTo(),
                alert.getAlertMessage(),
                alert.getSentAt()
        );
    }
}