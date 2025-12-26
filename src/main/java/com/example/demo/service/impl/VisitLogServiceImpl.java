package com.example.demo.service.impl;

import com.example.demo.model.Host;
import com.example.demo.model.VisitLog;
import com.example.demo.model.Visitor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitLogServiceImpl implements VisitLogService {

    private VisitLogRepository visitLogRepository;
    private VisitorRepository visitorRepository;
    private HostRepository hostRepository;

    // Existing constructor (for production)
    public VisitLogServiceImpl(VisitLogRepository visitLogRepository,
                               VisitorRepository visitorRepository,
                               HostRepository hostRepository) {
        this.visitLogRepository = visitLogRepository;
        this.visitorRepository = visitorRepository;
        this.hostRepository = hostRepository;
    }

    // No-arg constructor (for AuthTests.java)
    public VisitLogServiceImpl() {
        this.visitLogRepository = null;
        this.visitorRepository = null;
        this.hostRepository = null;
    }

    @Override
    public VisitLog checkInVisitor(Long visitorId, Long hostId, String purpose) {
        if (visitLogRepository == null || visitorRepository == null || hostRepository == null)
            throw new IllegalStateException("Repositories are not initialized");

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));

        VisitLog visitLog = new VisitLog();
        visitLog.setVisitor(visitor);
        visitLog.setHost(host);
        visitLog.setPurpose(purpose);
        visitLog.setCheckInTime(LocalDateTime.now());
        visitLog.setAccessGranted(true);
        visitLog.setAlertSent(false);

        return visitLogRepository.save(visitLog);
    }

    @Override
    public VisitLog checkOutVisitor(Long visitLogId) {
        if (visitLogRepository == null)
            throw new IllegalStateException("visitLogRepository is not initialized");

        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("VisitLog not found"));

        if (visitLog.getCheckInTime() == null) {
            throw new IllegalStateException("Visitor not checked in");
        }

        visitLog.setCheckOutTime(LocalDateTime.now());
        return visitLogRepository.save(visitLog);
    }

    @Override
    public List<VisitLog> getActiveVisits() {
        if (visitLogRepository == null)
            throw new IllegalStateException("visitLogRepository is not initialized");

        return visitLogRepository.findByCheckOutTimeIsNull();
    }

    @Override
    public VisitLog getVisitLog(Long id) {
        if (visitLogRepository == null)
            throw new IllegalStateException("visitLogRepository is not initialized");

        return visitLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisitLog not found"));
    }
}
