package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@model
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;

    private LocalDateTime appointmentTime;

    private String purpose;

    // Constructors
    public Appointment() {}
    public Appointment(Visitor visitor, Host host, LocalDateTime appointmentTime, String purpose) {
        this.visitor = visitor;
        this.host = host;
        this.appointmentTime = appointmentTime;
        this.purpose = purpose;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }
    public Host getHost() { return host; }
    public void setHost(Host host) { this.host = host; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
