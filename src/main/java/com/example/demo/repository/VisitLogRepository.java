package com.example.apiproject.repository;

import com.example.apiproject.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
 
    List<VisitLog> findByCheckOutTimeIsNull();
}
