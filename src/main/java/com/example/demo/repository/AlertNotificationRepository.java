package com.example.demo.repository;

import com.example.demo.model.AlertNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {

        List<AlertNotification> findByVisitLogId(Long id);
}
