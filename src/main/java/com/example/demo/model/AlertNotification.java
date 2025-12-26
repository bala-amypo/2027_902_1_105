package com.example.demo.model.;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@model
@Table(name = "alert_notifications")
public class AlertNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime alertTime;

    @ManyToOne
    @JoinColumn(name = "visit_log_id")
    private VisitLog visitLog;

    // Constructors
    public AlertNotification() {}
    public AlertNotification(String message, LocalDateTime alertTime, VisitLog visitLog) {
        this.message = message;
        this.alertTime = alertTime;
        this.visitLog = visitLog;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getAlertTime() { return alertTime; }
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }
    public VisitLog getVisitLog() { return visitLog; }
    public void setVisitLog(VisitLog visitLog) { this.visitLog = visitLog; }
}

