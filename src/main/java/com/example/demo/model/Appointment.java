package com.example.demo.model;

import java.time.LocalDateTime;

public class Appointment {

    private LocalDateTime appointmentDate;
    private String status;

    // getters and setters
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
