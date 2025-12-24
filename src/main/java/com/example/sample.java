MY FINAL PROJECT


config

OpenApiConfig.java

SecurityConfig.java

SwaggerConfig.java

package com.example.apiproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Digital Visitor Management API",
                version = "1.0",
                description = "APIs for authentication, visitors, hosts, appointments, visit logs and alerts"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT token required for API access",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
 
}

CONTROLLER

AlertNotificationController.java

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

AppointmentController.java

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


AuthController.java



package com.example.apiproject.controller;

import com.example.apiproject.dto.AuthRequest;
import com.example.apiproject.dto.AuthResponse;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "User registration and login")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register new user", description = "Create new user account")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody AuthRequest request) {
        userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "User registered successfully", null));
    }

    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
    }
}


HostController.java

package com.example.apiproject.controller;

import com.example.apiproject.dto.HostDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.HostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hosts", description = "Host/Employee management")
@RestController
@RequestMapping("/api/hosts")
@SecurityRequirement(name = "bearerAuth")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @Operation(summary = "Create new host")
    @PostMapping
    public ResponseEntity<ApiResponse> createHost(@Valid @RequestBody HostDTO hostDTO) {
        HostDTO created = hostService.createHost(hostDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Host created successfully", created));
    }

    @Operation(summary = "Get all hosts")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllHosts() {
        List<HostDTO> hosts = hostService.getAllHosts();
        return ResponseEntity.ok(new ApiResponse(true, "Hosts retrieved", hosts));
    }

    @Operation(summary = "Get host by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHost(
            @Parameter(description = "Host ID") @PathVariable Long id) {
        HostDTO host = hostService.getHost(id);
        return ResponseEntity.ok(new ApiResponse(true, "Host found", host));
    }
}


VisitLogController.java

package com.example.apiproject.controller;

import com.example.apiproject.dto.VisitLogDTO;
import com.example.apiproject.dto.ApiResponse;
import com.example.apiproject.service.VisitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Visit Logs", description = "Check-in and check-out operations")
@RestController
@RequestMapping("/api/visits")
@SecurityRequirement(name = "bearerAuth")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    @Operation(summary = "Check-in visitor")
    @PostMapping("/checkin/{visitorId}/{hostId}")
    public ResponseEntity<ApiResponse> checkInVisitor(
            @Parameter(description = "Visitor ID") @PathVariable Long visitorId,
            @Parameter(description = "Host ID") @PathVariable Long hostId,
            @RequestBody Map<String, String> request) {
        String purpose = request.get("purpose");
        VisitLogDTO visitLog = visitLogService.checkInVisitor(visitorId, hostId, purpose);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Visitor checked in", visitLog));
    }

    @Operation(summary = "Check-out visitor")
    @PostMapping("/checkout/{visitLogId}")
    public ResponseEntity<ApiResponse> checkOutVisitor(
            @Parameter(description = "Visit Log ID") @PathVariable Long visitLogId) {
        VisitLogDTO visitLog = visitLogService.checkOutVisitor(visitLogId);
        return ResponseEntity.ok(new ApiResponse(true, "Visitor checked out", visitLog));
    }

    @Operation(summary = "Get active visits")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveVisits() {
        List<VisitLogDTO> activeVisits = visitLogService.getActiveVisits();
        return ResponseEntity.ok(new ApiResponse(true, "Active visits", activeVisits));
    }

    @Operation(summary = "Get visit log by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVisitLog(
            @Parameter(description = "Visit Log ID") @PathVariable Long id) {
        VisitLogDTO visitLog = visitLogService.getVisitLog(id);
        return ResponseEntity.ok(new ApiResponse(true, "Visit log found", visitLog));
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

EXCEPTION

BadRequestException.java

package com.example.apiproject.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}


GlobalExceptionHandler.java

package com.example.apiproject.exception;

import com.example.apiproject.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

 
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> handleIllegalState(IllegalStateException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

 
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(BadRequestException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse response = new ApiResponse(false, "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        ApiResponse response = new ApiResponse(false,
            "Internal server error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


ResourceNotFoundHandler.java

package com.example.apiproject.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}



MODEL

AlertNotification.java

package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert_notifications")
public class AlertNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "visit_log_id", unique = true)
    private VisitLog visitLog;

    @NotBlank
    private String sentTo;

    @NotBlank
    @Column(length = 1000)
    private String alertMessage;

    private LocalDateTime sentAt;

    public AlertNotification() {
    }

    public AlertNotification(Long id, VisitLog visitLog, String sentTo,
                             String alertMessage, LocalDateTime sentAt) {
        this.id = id;
        this.visitLog = visitLog;
        this.sentTo = sentTo;
        this.alertMessage = alertMessage;
        this.sentAt = sentAt;
    }

    @PrePersist
    public void prePersist() {
        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitLog getVisitLog() {
        return visitLog;
    }

    public void setVisitLog(VisitLog visitLog) {
        this.visitLog = visitLog;
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


Appointment.java

package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Host host;

    @NotNull
    private LocalDate appointmentDate;

    private String purpose;

    private String status;

    public Appointment() {
    }

    public Appointment(Long id, Visitor visitor, Host host,
                       LocalDate appointmentDate, String purpose, String status) {
        this.id = id;
        this.visitor = visitor;
        this.host = host;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "SCHEDULED";
        }
        if (appointmentDate != null && appointmentDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("appointmentDate cannot be past");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
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


Host.java


package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "hosts", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String hostName;

    private String fullname;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String department;

    @NotBlank
    private String phone;

    private LocalDateTime createdAt;

    public Host() {
    }

    public Host(Long id, String hostName, String fullname, String email,
                String department, String phone, LocalDateTime createdAt) {
        this.id = id;
        this.hostName = hostName;
        this.fullname = fullname;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


User.java

package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    private String role;

    private LocalDateTime createdAt;

    public User() {
    }

    public User(Long id, String username, String email,
                String password, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = "USER";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


VisitLog.java

package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "visit_logs")
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "host_id")
    private Host host;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private String purpose;

    @NotNull
    private Boolean accessGranted;

    private Boolean alertSent = false;

    public VisitLog() {
    }

    public VisitLog(Long id, Visitor visitor, Host host,
                    LocalDateTime checkInTime, LocalDateTime checkOutTime,
                    String purpose, Boolean accessGranted, Boolean alertSent) {
        this.id = id;
        this.visitor = visitor;
        this.host = host;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.purpose = purpose;
        this.accessGranted = accessGranted;
        this.alertSent = alertSent;
    }

    @PrePersist
    public void prePersist() {
        if (checkInTime == null) {
            checkInTime = LocalDateTime.now();
        }
        if (alertSent == null) {
            alertSent = false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
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


Visitor.java

package com.example.apiproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String idProofNumber;

    private LocalDateTime createdAt;

    public Visitor() {
    }

    public Visitor(Long id, String fullName, String email, String phone, String idProofNumber, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.idProofNumber = idProofNumber;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

REPOSITORY

AlertNotificationRepository.java

package com.example.apiproject.repository;

import com.example.apiproject.model.AlertNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {
 
    Optional<AlertNotification> findByVisitLogId(Long visitLogId);
}


AppointmentRepository

package com.example.apiproject.repository;

import com.example.apiproject.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
 
    List<Appointment> findByHostId(Long hostId);
 
    List<Appointment> findByVisitorId(Long visitorId);
}


HostRepository.java

package com.example.apiproject.repository;

import com.example.apiproject.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
 
    Optional<Host> findByEmail(String email);
}


UserRepository.java

package com.example.apiproject.repository;

import com.example.apiproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 
    Optional<User> findByEmail(String email);
 
    Optional<User> findByUsername(String username);
}


VisitLogRepository.java

package com.example.apiproject.repository;

import com.example.apiproject.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
 
    List<VisitLog> findByCheckOutTimeIsNull();
}

VisitorRepository.java

package com.example.apiproject.repository;

import com.example.apiproject.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}

SECURITY

CustomUserDetails.java

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

JwtAuthentication.java

package com.example.apiproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.validateAndGetClaims(token).getSubject();
            } catch (Exception e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

JwtUtil.java

package com.example.apiproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
// import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final int jwtExpiration = 86400000; // 24 hours

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateAndGetClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = validateAndGetClaims(token).getSubject();
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return validateAndGetClaims(token).getExpiration().before(new Date());
    }
}

SERVICE

AlertNotificationService.java

package com.example.apiproject.service;

import com.example.apiproject.dto.AlertNotificationDTO;
import java.util.List;

public interface AlertNotificationService {
    AlertNotificationDTO sendAlert(Long visitLogId);
    AlertNotificationDTO getAlert(Long id);
    List<AlertNotificationDTO> getAllAlerts();
}


AppointmentService.java

package com.example.apiproject.service;

import com.example.apiproject.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(Long visitorId, Long hostId, AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointment(Long id);
    List<AppointmentDTO> getAppointmentsForHost(Long hostId);
    List<AppointmentDTO> getAppointmentsForVisitor(Long visitorId);
}


HostService.java

package com.example.apiproject.service;

import com.example.apiproject.dto.HostDTO;
import java.util.List;

public interface HostService {
    HostDTO createHost(HostDTO hostDTO);
    HostDTO getHost(Long id);
    List<HostDTO> getAllHosts();
    HostDTO getHostByEmail(String email);
}


UserService.java

package com.example.apiproject.service;

import java.util.List;

import com.example.apiproject.dto.AuthRequest;
import com.example.apiproject.dto.AuthResponse;
import com.example.apiproject.model.User;

public interface UserService {
    User registerUser(AuthRequest request);
    AuthResponse login(AuthRequest request);
    User createUser(User user);
    User getUser(Long id);
    User getByUsername(String username);
    List<User> getAllUsers();
}


VisitLogService.java

package com.example.apiproject.service;

import com.example.apiproject.dto.VisitLogDTO;
import java.util.List;

public interface VisitLogService {
    VisitLogDTO checkInVisitor(Long visitorId, Long hostId, String purpose);
    VisitLogDTO checkOutVisitor(Long visitLogId);
    List<VisitLogDTO> getActiveVisits();
    VisitLogDTO getVisitLog(Long id);
}


VisitorService.java

package com.example.apiproject.service;

import com.example.apiproject.dto.VisitorDTO;
import java.util.List;

public interface VisitorService {
    VisitorDTO createVisitor(VisitorDTO visitorDTO);
    VisitorDTO getVisitor(Long id);
    List<VisitorDTO> getAllVisitors();
}


IMPL

AlertNotificationServiceimpl.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.AlertNotificationDTO;
import com.example.apiproject.model.AlertNotification;
import com.example.apiproject.model.Host;
import com.example.apiproject.model.VisitLog;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.AlertNotificationRepository;
import com.example.apiproject.repository.VisitLogRepository;
import com.example.apiproject.service.AlertNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertNotificationServiceImpl implements AlertNotificationService {

    private final AlertNotificationRepository alertRepository;
    private final VisitLogRepository visitLogRepository;

    public AlertNotificationServiceImpl(AlertNotificationRepository alertRepository,
                                        VisitLogRepository visitLogRepository) {
        this.alertRepository = alertRepository;
        this.visitLogRepository = visitLogRepository;
    }

    @Override
    public AlertNotificationDTO sendAlert(Long visitLogId) {
        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));

        if (alertRepository.findByVisitLogId(visitLogId).isPresent()) {
            throw new IllegalArgumentException("Alert already sent");
        }

        Host host = visitLog.getHost();
        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(visitLog);
        alert.setSentTo(host.getEmail());
        alert.setAlertMessage("Visitor " + visitLog.getVisitor().getFullName() + 
                             " has checked in for visit purpose: " + visitLog.getPurpose());

        AlertNotification saved = alertRepository.save(alert);
        return modelToDto(saved);
    }

    @Override
    public AlertNotificationDTO getAlert(Long id) {
        AlertNotification alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
        return modelToDto(alert);
    }

    @Override
    public List<AlertNotificationDTO> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private AlertNotificationDTO modelToDto(AlertNotification alert) {
        return new AlertNotificationDTO(
                alert.getId(),
                alert.getVisitLog().getId(),
                alert.getSentTo(),
                alert.getAlertMessage(),
                alert.getSentAt()
        );
    }
}


AppointmentServiceimp.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.AppointmentDTO;
import com.example.apiproject.model.Appointment;
import com.example.apiproject.model.Host;
import com.example.apiproject.model.Visitor;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.AppointmentRepository;
import com.example.apiproject.repository.HostRepository;
import com.example.apiproject.repository.VisitorRepository;
import com.example.apiproject.service.AppointmentService;
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


HostServiceimpl.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.HostDTO;
import com.example.apiproject.model.Host;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.HostRepository;
import com.example.apiproject.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public HostDTO createHost(HostDTO dto) {
        Host host = dtoTomodel(dto);
        Host saved = hostRepository.save(host);
        return modelToDto(saved);
    }

    @Override
    public HostDTO getHost(Long id) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));
        return modelToDto(host);
    }

    @Override
    public List<HostDTO> getAllHosts() {
        return hostRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HostDTO getHostByEmail(String email) {
        Host host = hostRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));
        return modelToDto(host);
    }

    private Host dtoTomodel(HostDTO dto) {
        Host host = new Host();
        host.setId(dto.getId());
        host.setHostName(dto.getHostName());
        host.setFullname(dto.getFullname());
        host.setEmail(dto.getEmail());
        host.setDepartment(dto.getDepartment());
        host.setPhone(dto.getPhone());
        return host;
    }

    private HostDTO modelToDto(Host host) {
        return new HostDTO(
                host.getId(),
                host.getHostName(),
                host.getFullname(),
                host.getEmail(),
                host.getDepartment(),
                host.getPhone()
        );
    }
}

UserServiceimpl.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.AuthRequest;
import com.example.apiproject.dto.AuthResponse;
import com.example.apiproject.model.User;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.UserRepository;
import com.example.apiproject.security.JwtUtil;
import com.example.apiproject.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User registerUser(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getEmail()); // Use email as username
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}


VisitLogServiceimpl.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.VisitLogDTO;
import com.example.apiproject.model.Host;
import com.example.apiproject.model.VisitLog;
import com.example.apiproject.model.Visitor;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.HostRepository;
import com.example.apiproject.repository.VisitLogRepository;
import com.example.apiproject.repository.VisitorRepository;
import com.example.apiproject.service.VisitLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorRepository visitorRepository;
    private final HostRepository hostRepository;

    public VisitLogServiceImpl(VisitLogRepository visitLogRepository,
                               VisitorRepository visitorRepository,
                               HostRepository hostRepository) {
        this.visitLogRepository = visitLogRepository;
        this.visitorRepository = visitorRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public VisitLogDTO checkInVisitor(Long visitorId, Long hostId, String purpose) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));

        VisitLog visitLog = new VisitLog();
        visitLog.setVisitor(visitor);
        visitLog.setHost(host);
        visitLog.setPurpose(purpose);
        visitLog.setAccessGranted(true);
        visitLog.setAlertSent(false);

        VisitLog saved = visitLogRepository.save(visitLog);
        return modelToDto(saved);
    }

    @Override
    public VisitLogDTO checkOutVisitor(Long visitLogId) {
        VisitLog visitLog = visitLogRepository.findById(visitLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));
        
        if (visitLog.getCheckOutTime() != null) {
            throw new IllegalStateException("Visitor not checked in");
        }

        visitLog.setCheckOutTime(LocalDateTime.now());
        VisitLog updated = visitLogRepository.save(visitLog);
        return modelToDto(updated);
    }

    @Override
    public List<VisitLogDTO> getActiveVisits() {
        return visitLogRepository.findByCheckOutTimeIsNull().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VisitLogDTO getVisitLog(Long id) {
        VisitLog visitLog = visitLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit log not found"));
        return modelToDto(visitLog);
    }

    private VisitLogDTO modelToDto(VisitLog visitLog) {
        return new VisitLogDTO(
                visitLog.getId(),
                visitLog.getVisitor().getId(),
                visitLog.getHost().getId(),
                visitLog.getCheckInTime(),
                visitLog.getCheckOutTime(),
                visitLog.getPurpose(),
                visitLog.getAccessGranted(),
                visitLog.getAlertSent()
        );
    }
}


VisiterServiceimpl.java

package com.example.apiproject.service.impl;

import com.example.apiproject.dto.VisitorDTO;
import com.example.apiproject.model.Visitor;
import com.example.apiproject.exception.ResourceNotFoundException;
import com.example.apiproject.repository.VisitorRepository;
import com.example.apiproject.service.VisitorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public VisitorDTO createVisitor(VisitorDTO dto) {
        Visitor visitor = dtoTomodel(dto);
        Visitor saved = visitorRepository.save(visitor);
        return modelToDto(saved);
    }

    @Override
    public VisitorDTO getVisitor(Long id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
        return modelToDto(visitor);
    }

    @Override
    public List<VisitorDTO> getAllVisitors() {
        return visitorRepository.findAll().stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    private Visitor dtoTomodel(VisitorDTO dto) {
        Visitor visitor = new Visitor();
        visitor.setId(dto.getId());
        visitor.setFullName(dto.getFullName());
        visitor.setEmail(dto.getEmail());
        visitor.setPhone(dto.getPhone());
        visitor.setIdProofNumber(dto.getIdProofNumber());
        return visitor;
    }

    private VisitorDTO modelToDto(Visitor visitor) {
        return new VisitorDTO(
                visitor.getId(),
                visitor.getFullName(),
                visitor.getEmail(),
                visitor.getPhone(),
                visitor.getIdProofNumber()
        );
    }
}


