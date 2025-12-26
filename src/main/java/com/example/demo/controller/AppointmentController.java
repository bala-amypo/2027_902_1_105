package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestParam Long visitorId,
            @RequestParam Long hostId,
            @RequestBody Appointment appointment) {

        return ResponseEntity.ok(
                appointmentService.createAppointment(visitorId, hostId, appointment)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Appointment>> getByHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsForHost(hostId)
        );
    }

    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<Appointment>> getByVisitor(@PathVariable Long visitorId) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsForVisitor(visitorId)
        );
    }
}
