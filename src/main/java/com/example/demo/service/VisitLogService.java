
package com.example.demo.service;

import com.example.demo.dto.VisitLogDTO;
import java.util.List;

public interface VisitLogService {
    VisitLogDTO checkInVisitor(Long visitorId, Long hostId, String purpose);
    VisitLogDTO checkOutVisitor(Long visitLogId);
    List<VisitLogDTO> getActiveVisits();
    VisitLogDTO getVisitLog(Long id);
}