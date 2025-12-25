package com.example.demo.controller;

import com.example.demo.dto.AppointmentDTO;
import com.example.demo.dto.ApiResponse;
import com.example.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Appointments")
@RestController
@RequestMapping("/api/appointments")
@SecurityRequirement(name = "bearer")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Create appointment")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AppointmentDTO dto) {
        AppointmentDTO saved = appointmentService.createAppointment(dto);
        return ResponseEntity.ok(new ApiResponse(true, "Appointment created", saved));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Appointment found", appointment));
    }

    @GetMapping("/host/{hostId}")
    @Operation(summary = "Get appointments by host")
    public ResponseEntity<ApiResponse> getByHost(@PathVariable Long hostId) {
        List<AppointmentDTO> appointments = appointmentService.getByHostId(hostId);
        return ResponseEntity.ok(new ApiResponse(true, "Appointments found", appointments));
    }

    @GetMapping("/visitor/{visitorId}")
    @Operation(summary = "Get appointments by visitor")
    public ResponseEntity<ApiResponse> getByVisitor(@PathVariable Long visitorId) {
        List<AppointmentDTO> appointments = appointmentService.getByVisitorId(visitorId);
        return ResponseEntity.ok(new ApiResponse(true, "Appointments found", appointments));
    }
}
