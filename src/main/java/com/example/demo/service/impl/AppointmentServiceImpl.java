
package com.example.demo.service.impl;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.model.Appointment;
import com.example.demo.model.Host;
import com.example.demo.model.Visitor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public AppointmentDTO createAppointment(Long visitorId, Long hostId, AppointmentDTO dto) {
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("appointmentDate cannot be past");
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));

        Appointment appointment = new Appointment();
        appointment.setVisitor(visitor);
        appointment.setHost(host);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setPurpose(dto.getPurpose());
        appointment.setStatus("SCHEDULED");

        Appointment saved = appointmentRepository.save(appointment);
        return modelToDto(saved);
    }

    @Override
    public AppointmentDTO getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return modelToDto(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsForHost(Long hostId) {
        return appointmentRepository.findByHostId(hostId).stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsForVisitor(Long visitorId) {
        return appointmentRepository.findByVisitorId(visitorId).stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private AppointmentDTO modelToDto(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getVisitor().getId(),
                appointment.getHost().getId(),
                appointment.getAppointmentDate(),
                appointment.getPurpose(),
                appointment.getStatus()
        );
    }
}
