package com.example.apiproject.service;

import com.example.apiproject.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(Long visitorId, Long hostId, AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointment(Long id);
    List<AppointmentDTO> getAppointmentsForHost(Long hostId);
    List<AppointmentDTO> getAppointmentsForVisitor(Long visitorId);
}
