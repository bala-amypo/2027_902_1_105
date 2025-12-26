
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@model
@Table(name = "visit_logs")
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    // Constructors
    public VisitLog() {}
    public VisitLog(Visitor visitor, Host host, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.visitor = visitor;
        this.host = host;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }
    public Host getHost() { return host; }
    public void setHost(Host host) { this.host = host; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
}
