VisitorRepository.java


HostRepository.java


AppointmentRepository.java


VisitLogRepository.java

package com.example.demo.repository;

import com.example.demo.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    List<VisitLog> findByCheckOutTimeIsNull();
}

AlertNotification.java

package com.example.demo.repository;

import com.example.demo.entity.AlertNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {

    Optional<AlertNotification> findByVisitLogId(Long visitLogId);
}


UserRepository.java

package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}



