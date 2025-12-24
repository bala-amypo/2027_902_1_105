package com.example.apiproject.service.impl;

import com.example.apiproject.dto.VisitLogDTO;
import com.example.apiproject.model.Host;
import com.example.apiproject.model.VisitLog;
import com.example.apiproject.model.Visitor;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.HostRepository;
import com.example.apiproject.repository.VisitLogRepository;
import com.example.apiproject.repository.VisitorRepository;
import com.example.apiproject.service.VisitLogService;
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

