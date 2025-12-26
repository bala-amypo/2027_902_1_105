package com.example.demo.service.impl;

import com.example.demo.model.Appointment;
import com.example.demo.model.Host;
import com.example.demo.model.Visitor;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Override
    public Appointment scheduleAppointment(Visitor visitor, Host host, LocalDate appointmentDate, String purpose) {
        Appointment appointment = new Appointment();
        appointment.setVisitor(visitor);
        appointment.setHost(host);
        appointment.setAppointmentDate(appointmentDate.atStartOfDay()); // convert LocalDate → LocalDateTime
        appointment.setPurpose(purpose);
        appointment.setStatus("Scheduled"); // default status

        return appointmentRepo.save(appointment);
    }

    @Override
    public Appointment updateStatus(Appointment appointment, String status) {
        appointment.setStatus(status);
        return appointmentRepo.save(appointment);
    }
}
