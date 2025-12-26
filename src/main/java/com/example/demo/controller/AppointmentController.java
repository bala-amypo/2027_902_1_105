package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "Appointment scheduling")
@SecurityRequirement(name = "Bearer Authentication")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{visitorId}/{hostId}")
    @Operation(summary = "Create a new appointment")
    public ResponseEntity<ApiResponse<Appointment>> createAppointment(
            @PathVariable Long visitorId,
            @PathVariable Long hostId,
            @Valid @RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.createAppointment(visitorId, hostId, appointment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Appointment created successfully", createdAppointment));
    }

    @GetMapping("/host/{hostId}")
    @Operation(summary = "Get appointments for a host")
    public ResponseEntity<ApiResponse<List<Appointment>>> getAppointmentsForHost(@PathVariable Long hostId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForHost(hostId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Appointments retrieved successfully", appointments));
    }

    @GetMapping("/visitor/{visitorId}")
    @Operation(summary = "Get appointments for a visitor")
    public ResponseEntity<ApiResponse<List<Appointment>>> getAppointmentsForVisitor(@PathVariable Long visitorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForVisitor(visitorId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Appointments retrieved successfully", appointments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<ApiResponse<Appointment>> getAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointment(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Appointment retrieved successfully", appointment));
    }
}