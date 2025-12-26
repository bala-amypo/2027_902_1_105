package com.example.demo.service.impl;

import com.example.demo.model.Host;
import com.example.demo.model.VisitLog;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.VisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogRepository visitLogRepo;

    @Override
    public VisitLog createVisitLog(Visitor visitor, Host host, String purpose) {
        VisitLog log = new VisitLog();
        log.setVisitor(visitor);
        log.setHost(host);
        log.setPurpose(purpose);
        log.setCheckInTime(LocalDateTime.now());
        log.setAccessGranted(true);

        return visitLogRepo.save(log);
    }

    @Override
    public VisitLog checkOutVisit(VisitLog log) {
        log.setCheckOutTime(LocalDateTime.now());
        log.setAccessGranted(false);

        return visitLogRepo.save(log);
    }
}
