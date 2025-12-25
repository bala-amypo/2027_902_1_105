package com.example.apiproject.repository;

import com.example.apiproject.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
 
    List<Appointment> findByHostId(Long hostId);
 
    List<Appointment> findByVisitorId(Long visitorId);
}