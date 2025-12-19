package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // exact naming required
    List<Appointment> findByVisitorId(Long id);

    List<Appointment> findByHostId(Long id);
}
