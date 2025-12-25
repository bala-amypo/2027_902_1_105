package com.example.demo.service;

import com.example.demo.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {

    AppointmentDTO createAppointment(Long visitorId, Long hostId, AppointmentDTO dto);

    AppointmentDTO getAppointment(Long id);

    List<AppointmentDTO> getAppointmentsForHost(Long hostId);

    List<AppointmentDTO> getAppointmentsForVisitor(Long visitorId);
}
