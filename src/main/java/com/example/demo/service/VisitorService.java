package com.example.demo.service;

import com.example.demo.dto.VisitorDTO;
import java.util.List;

public interface VisitorService {
    VisitorDTO createVisitor(VisitorDTO visitorDTO);
    VisitorDTO getVisitor(Long id);
    List<VisitorDTO> getAllVisitors();
}