package com.example.apiproject.service;

import com.example.apiproject.dto.VisitorDTO;
import java.util.List;

public interface VisitorService {
    VisitorDTO createVisitor(VisitorDTO visitorDTO);
    VisitorDTO getVisitor(Long id);
    List<VisitorDTO> getAllVisitors();
}