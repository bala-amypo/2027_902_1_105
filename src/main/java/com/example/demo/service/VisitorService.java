package com.example.demo.service;

import com.example.demo.model.Visitor;
import java.util.List;

public interface VisitorService {
    Visitor createVisitor(Visitor visitor);
    Visitor getVisitor(Long id);
    List<Visitor> getAllVisitors();
}