package com.example.demo.service.impl;

import com.example.demo.dto.VisitLogDTO;
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
import java.util.stream.Collectors;

@Service
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorRepository visitorRepository;
    private final HostRepository hostRepository;

    public VisitLogServiceImpl(VisitLogRepository visitLogRepository,
                               VisitorRepository visitorRepository,
                               HostRepository hostRepository) {
        this.visitLogRepository = visitLogRepository;
        this.visitorRepository = visitorRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public VisitLogDTO checkInVisitor(Long visitorId, Long hostId, String purpose) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));

        VisitLog visitLog = new VisitLog();
        visitLog.setVisitor(visitor);
        visitLog.setHost(host);
        visitLog.setPurpose(purpose);
        visitLog.setAccessGranted(true);
        visitLog.setAlertSent(false);

        VisitLog saved = visitLogRepository.save(visitLog);
        return modelToDto(saved);
    }

    @Override
    public VisitLogDTO checkOutVisitor(Long visitLogId) {
        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));
        
        if (visitLog.getCheckOutTime() != null) {
            throw new IllegalStateException("Visitor not checked in");
        }

        visitLog.setCheckOutTime(LocalDateTime.now());
        VisitLog updated = visitLogRepository.save(visitLog);
        return modelToDto(updated);
    }

    @Override
    public List<VisitLogDTO> getActiveVisits() {
        return visitLogRepository.findByCheckOutTimeIsNull().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitLogDTO getVisitLog(Long id) {
        VisitLog visitLog = visitLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));
        return modelToDto(visitLog);
    }

    private VisitLogDTO modelToDto(VisitLog visitLog) {
        return new VisitLogDTO(
                visitLog.getId(),
                visitLog.getVisitor().getId(),
                visitLog.getHost().getId(),
                visitLog.getCheckInTime(),
                visitLog.getCheckOutTime(),
                visitLog.getPurpose(),
                visitLog.getAccessGranted(),
                visitLog.getAlertSent()
        );
    }
}

