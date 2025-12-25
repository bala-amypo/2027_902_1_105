package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Host host;

    @NotNull
    private LocalDate appointmentDate;

    private String purpose;

    private String status;

    public Appointment() {
    }

    public Appointment(Long id, Visitor visitor, Host host,
                       LocalDate appointmentDate, String purpose, String status) {
        this.id = id;
        this.visitor = visitor;
        this.host = host;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "SCHEDULED";
        }
        if (appointmentDate != null && appointmentDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("appointmentDate cannot be past");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
