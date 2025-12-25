package com.example.apiproject.controller;

import com.example.apiproject.dto.AlertNotificationDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.AlertNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alerts", description = "Alert notifications to hosts")
@RestController
@RequestMapping("/api/alerts")
@SecurityRequirement(name = "bearerAuth")
public class AlertNotificationController {

    private final AlertNotificationService alertNotificationService;

    public AlertNotificationController(AlertNotificationService alertNotificationService) {
        this.alertNotificationService = alertNotificationService;
    }

    @Operation(summary = "Send alert for visit log")
    @PostMapping("/send/{visitLogId}")
    public ResponseEntity<ApiResponse> sendAlert(
            @Parameter(description = "Visit Log ID") @PathVariable Long visitLogId) {
        AlertNotificationDTO alert = alertNotificationService.sendAlert(visitLogId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Alert sent successfully", alert));
    }

    @Operation(summary = "Get all alerts")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAlerts() {
        List<AlertNotificationDTO> alerts = alertNotificationService.getAllAlerts();
        return ResponseEntity.ok(new ApiResponse(true, "Alerts retrieved", alerts));
    }

    @Operation(summary = "Get alert by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAlert(
            @Parameter(description = "Alert ID") @PathVariable Long id) {
        AlertNotificationDTO alert = alertNotificationService.getAlert(id);
        return ResponseEntity.ok(new ApiResponse(true, "Alert found", alert));
    }
}


















VisitorController.java

package com.example.apiproject.controller;

import com.example.apiproject.dto.VisitorDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Visitors", description = "Visitor management endpoints")
@RestController
@RequestMapping("/api/visitors")
@SecurityRequirement(name = "bearerAuth")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @Operation(summary = "Create new visitor")
    @PostMapping
    public ResponseEntity<ApiResponse> createVisitor(@Valid @RequestBody VisitorDTO visitorDTO) {
        VisitorDTO created = visitorService.createVisitor(visitorDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Visitor created successfully", created));
    }

    @Operation(summary = "Get all visitors")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllVisitors() {
        List<VisitorDTO> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(new ApiResponse(true, "Visitors retrieved", visitors));
    }

    @Operation(summary = "Get visitor by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVisitor(
            @Parameter(description = "Visitor ID") @PathVariable Long id) {
        VisitorDTO visitor = visitorService.getVisitor(id);
        return ResponseEntity.ok(new ApiResponse(true, "Visitor found", visitor));
    }
}


DTO

AlertNotificationDTO.java

package com.example.apiproject.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AlertNotificationDTO {

    private Long id;
 
    private Long visitLogId;
 
    @NotBlank(message = "Recipient email is required")
    private String sentTo;
 
    @NotBlank(message = "Alert message is required")
    private String alertMessage;
 
    private LocalDateTime sentAt;

 
    public AlertNotificationDTO() {}

 
    public AlertNotificationDTO(Long id, Long visitLogId, String sentTo,
                               String alertMessage, LocalDateTime sentAt) {
        this.id = id;
        this.visitLogId = visitLogId;
        this.sentTo = sentTo;
        this.alertMessage = alertMessage;
        this.sentAt = sentAt;
    }

 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitLogId() {
        return visitLogId;
    }

    public void setVisitLogId(Long visitLogId) {
        this.visitLogId = visitLogId;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}

ApiResponse.java

package com.example.apiproject.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

AppointmentDTO.java

package com.example.apiproject.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AppointmentDTO {
    private Long id;
    private Long visitorId;
    private Long hostId;
 
    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;
 
    private String purpose;
    private String status;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, Long visitorId, Long hostId, LocalDate appointmentDate, String purpose, String status) {
        this.id = id;
        this.visitorId = visitorId;
        this.hostId = hostId;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


AuthRequest.java


package com.example.apiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
 
    @NotBlank(message = "Password is required")
    private String password;

    public AuthRequest() {}

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


AuthResponse.java

package com.example.apiproject.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, Long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


HostDTO.java

package com.example.apiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class HostDTO {
    private Long id;
 
    @NotBlank(message = "Host name is required")
    private String hostName;
 
    private String fullname;
 
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
 
    @NotBlank(message = "Department is required")
    private String department;
 
    @NotBlank(message = "Phone is required")
    private String phone;

    public HostDTO() {}

    public HostDTO(Long id, String hostName, String fullname, String email, String department, String phone) {
        this.id = id;
        this.hostName = hostName;
        this.fullname = fullname;
        this.email = email;
        this.department = department;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


VisitLogDTO.java

package com.example.apiproject.dto;

import java.time.LocalDateTime;

public class VisitLogDTO {
    private Long id;
    private Long visitorId;
    private Long hostId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String purpose;
    private Boolean accessGranted;
    private Boolean alertSent;

    public VisitLogDTO() {}

    public VisitLogDTO(Long id, Long visitorId, Long hostId, LocalDateTime checkInTime,
                      LocalDateTime checkOutTime, String purpose, Boolean accessGranted, Boolean alertSent) {
        this.id = id;
        this.visitorId = visitorId;
        this.hostId = hostId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.purpose = purpose;
        this.accessGranted = accessGranted;
        this.alertSent = alertSent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean getAccessGranted() {
        return accessGranted;
    }

    public void setAccessGranted(Boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

    public Boolean getAlertSent() {
        return alertSent;
    }

    public void setAlertSent(Boolean alertSent) {
        this.alertSent = alertSent;
    }
}

VisitorDTO.java

package com.example.apiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VisitorDTO {
    private Long id;
 
    @NotBlank(message = "Full name is required")
    private String fullName;
 
    @Email(message = "Invalid email format")
    private String email;
 
    @NotBlank(message = "Phone is required")
    private String phone;
 
    @NotBlank(message = "ID proof number is required")
    private String idProofNumber;

    public VisitorDTO() {}

    public VisitorDTO(Long id, String fullName, String email, String phone, String idProofNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.idProofNumber = idProofNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdProofNumber() {
        return idProofNumber;
    }

    public void setIdProofNumber(String idProofNumber) {
        this.idProofNumber = idProofNumber;
    }
}
