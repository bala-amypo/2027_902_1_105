
package com.example.apiproject.service;

import com.example.apiproject.dto.VisitLogDTO;
import java.util.List;

public interface VisitLogService {
    VisitLogDTO checkInVisitor(Long visitorId, Long hostId, String purpose);
    VisitLogDTO checkOutVisitor(Long visitLogId);
    List<VisitLogDTO> getActiveVisits();
    VisitLogDTO getVisitLog(Long id);
}