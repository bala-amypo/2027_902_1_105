package com.example.apiproject.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AlertNotificationDTO {

    private Long id;
 
    private Long visitLogId;
 
    @NotBlank(message = "Recipient email is required")
    private String sentTo;
 
    @NotBlank(message = "Alert message is required")
    private String alertMessage;
 
    private LocalDateTime sentAt;

 
    public AlertNotificationDTO() {}

 
    public AlertNotificationDTO(Long id, Long visitLogId, String sentTo,
                               String alertMessage, LocalDateTime sentAt) {
        this.id = id;
        this.visitLogId = visitLogId;
        this.sentTo = sentTo;
        this.alertMessage = alertMessage;
        this.sentAt = sentAt;
    }

 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitLogId() {
        return visitLogId;
    }

    public void setVisitLogId(Long visitLogId) {
        this.visitLogId = visitLogId;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
