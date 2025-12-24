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


AppointmentController.java




AuthController.java






HostController.java



VisitLogController.java


VisitorController.java



DTO


ApiResponse.java



AppointmentDTO.java



AuthRequest.java




AuthResponse.java



HostDTO.java



VisitLogDTO.java



VisitorDTO.java


EXCEPTION

BadRequestException.java




GlobalExceptionHandler.java



ResourceNotFoundHandler.java



MODEL

AlertNotification.java


Appointment.java

Host.java





User.java



VisitLog.java


Visitor.java



REPOSITORY

AlertNotificationRepository.java



AppointmentRepository




HostRepository.java



UserRepository.java


VisitLogRepository.java


VisitorRepository.java

SECURITY

CustomUserDetails.java


JwtAuthentication.java


JwtUtil.java


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


