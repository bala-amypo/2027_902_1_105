package com.example.apiproject.controller;

import com.example.apiproject.dto.AppointmentDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Appointments", description = "Appointment scheduling")
@RestController
@RequestMapping("/api/appointments")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Create appointment")
    @PostMapping("/{visitorId}/{hostId}")
    public ResponseEntity<ApiResponse> createAppointment(
            @Parameter(description = "Visitor ID") @PathVariable Long visitorId,
            @Parameter(description = "Host ID") @PathVariable Long hostId,
            @Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO created = appointmentService.createAppointment(visitorId, hostId, appointmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Appointment created", created));
    }

    @Operation(summary = "Get appointment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAppointment(
            @Parameter(description = "Appointment ID") @PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointment(id);
        return ResponseEntity.ok(new ApiResponse(true, "Appointment found", appointment));
    }

    @Operation(summary = "Get appointments by host")
    @GetMapping("/host/{hostId}")
    public ResponseEntity<ApiResponse> getAppointmentsForHost(
            @Parameter(description = "Host ID") @PathVariable Long hostId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsForHost(hostId);
        return ResponseEntity.ok(new ApiResponse(true, "Host appointments", appointments));
    }

    @Operation(summary = "Get appointments by visitor")
    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<ApiResponse> getAppointmentsForVisitor(
            @Parameter(description = "Visitor ID") @PathVariable Long visitorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsForVisitor(visitorId);
        return ResponseEntity.ok(new ApiResponse(true, "Visitor appointments", appointments));
    }
}
