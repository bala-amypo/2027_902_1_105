package com.example.demo.service.impl;

import com.example.demo.model.Appointment;
import com.example.demo.model.Host;
import com.example.demo.model.Visitor;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final VisitorRepository visitorRepository;
    private final HostRepository hostRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  VisitorRepository visitorRepository,
                                  HostRepository hostRepository) {
        this.appointmentRepository = appointmentRepository;
        this.visitorRepository = visitorRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public Appointment createAppointment(Long visitorId, Long hostId, Appointment appointment) {
        Visitor visitor = visitorRepository.findById(visitorId).orElse(null);
        Host host = hostRepository.findById(hostId).orElse(null);

        if (visitor == null || host == null) {
            return null;
        }

        LocalDateTime date = appointment.getAppointmentDate();
        if (date != null && date.isBefore(LocalDateTime.now())) {
            // no exception; just return null to indicate invalid
            return null;
        }

        appointment.setVisitor(visitor);
        appointment.setHost(host);
        if (appointment.getStatus() == null) {
            appointment.setStatus(Appointment.Status.SCHEDULED);
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAppointmentsForHost(Long hostId) {
        return appointmentRepository.findByHostId(hostId);
    }

    @Override
    public List<Appointment> getAppointmentsForVisitor(Long visitorId) {
        return appointmentRepository.findByVisitorId(visitorId);
    }
}
