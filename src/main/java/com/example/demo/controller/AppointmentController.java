package com.example.demo.controller;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // CREATE appointment
    @PostMapping
    public AppointmentDTO createAppointment(
            @RequestParam Long visitorId,
            @RequestParam Long hostId,
            @Valid @RequestBody AppointmentDTO dto) {

        return appointmentService.createAppointment(visitorId, hostId, dto);
    }

    // GET appointment by id
    @GetMapping("/{id}")
    public AppointmentDTO getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    // GET appointments for host
    @GetMapping("/host/{hostId}")
    public List<AppointmentDTO> getAppointmentsForHost(@PathVariable Long hostId) {
        return appointmentService.getAppointmentsForHost(hostId);
    }

    // GET appointments for visitor
    @GetMapping("/visitor/{visitorId}")
    public List<AppointmentDTO> getAppointmentsForVisitor(@PathVariable Long visitorId) {
        return appointmentService.getAppointmentsForVisitor(visitorId);
    }
}
