
package com.example.demo.repository;

import com.example.demo.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    List<VisitLog> findByCheckOutTimeIsNull();
}