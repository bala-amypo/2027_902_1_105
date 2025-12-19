MODEL

AlertNotification.java


Appointment.java

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Visitor visitor;

    @ManyToOne(optional = false)
    private Host host;

    private LocalDateTime appointmentDate;

    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.SCHEDULED;

    public enum Status {
        SCHEDULED,
        CANCELLED,
        COMPLETED
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}


Host.java

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hostName;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String phone;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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
}


User.java

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ADMIN / USER

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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
}


VisitLog.java

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Visitor visitor;

    @ManyToOne(optional = false)
    private Host host;

    private LocalDateTime checkinTime;

    private LocalDateTime checkoutTime;

    private String purpose;

    private Boolean accessGranted;

    @Column(nullable = false)
    private Boolean alertSent = false;

    @PrePersist
    public void prePersist() {
        if (checkinTime == null) {
            checkinTime = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
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

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String idProofNumber;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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
}


CONTROLLER:

AlertNotificationc.java
package com.example.demo.controller;

import com.example.demo.model.AlertNotification;
import com.example.demo.service.AlertNotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "AlertNotification")
public class AlertNotificationController {

    private final AlertNotificationService alertService;

    public AlertNotificationController(AlertNotificationService alertService) {
        this.alertService = alertService;
    }

    // POST /send/{visitLogId} – send alert
    @PostMapping("/send/{visitLogId}")
    public ResponseEntity<AlertNotification> sendAlert(@PathVariable Long visitLogId) {
        return ResponseEntity.ok(alertService.sendAlert(visitLogId));
    }

    // GET /{id} – get alert
    @GetMapping("/{id}")
    public ResponseEntity<AlertNotification> getAlert(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlert(id));
    }

    // GET / – list all alerts
    @GetMapping
    public ResponseEntity<List<AlertNotification>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }
}

Appointmentcontroller.java

package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // POST /{visitorId}/{hostId} – create appointment
    @PostMapping("/{visitorId}/{hostId}")
    public ResponseEntity<Appointment> createAppointment(@PathVariable Long visitorId,
                                                         @PathVariable Long hostId,
                                                         @RequestBody Appointment appointment) {
        return ResponseEntity.ok(
                appointmentService.createAppointment(visitorId, hostId, appointment)
        );
    }

    // GET /host/{hostId} – list for host
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForHost(hostId));
    }

    // GET /visitor/{visitorId} – list for visitor
    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForVisitor(@PathVariable Long visitorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForVisitor(visitorId));
    }

    // GET /{id} – get appointment
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }
}

AuthController.java

package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register using User entity directly
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    // Simple login without DTO or security, returns plain string message
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        User user = userService.getByUsername(username);
        if (user == null || !password.equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        return ResponseEntity.ok("Login successful");
    }
}

HostController.java

package com.example.demo.controller;

import com.example.demo.model.Host;
import com.example.demo.service.HostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosts")
@Tag(name = "Host")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping
    public ResponseEntity<Host> createHost(@RequestBody Host host) {
        return ResponseEntity.ok(hostService.createHost(host));
    }

    @GetMapping
    public ResponseEntity<List<Host>> getAllHosts() {
        return ResponseEntity.ok(hostService.getAllHosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Host> getHost(@PathVariable Long id) {
        return ResponseEntity.ok(hostService.getHost(id));
    }
}

VisitLogController.java

package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@Tag(name = "VisitLog")
public class VisitLogController {

    private final VisitLogService visitLogService;

    public VisitLogController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    // POST /checkin/{visitorId}/{hostId} – check in
    @PostMapping("/checkin/{visitorId}/{hostId}")
    public ResponseEntity<VisitLog> checkin(@PathVariable Long visitorId,
                                            @PathVariable Long hostId,
                                            @RequestParam String purpose) {
        return ResponseEntity.ok(
                visitLogService.checkInVisitor(visitorId, hostId, purpose)
        );
    }

    // POST /checkout/{visitLogId} – check out
    @PostMapping("/checkout/{visitLogId}")
    public ResponseEntity<VisitLog> checkout(@PathVariable Long visitLogId) {
        return ResponseEntity.ok(visitLogService.checkOutVisitor(visitLogId));
    }

    // GET /active – list active visits
    @GetMapping("/active")
    public ResponseEntity<List<VisitLog>> getActiveVisits() {
        return ResponseEntity.ok(visitLogService.getActiveVisits());
    }

    // GET /{id} – get visit log
    @GetMapping("/{id}")
    public ResponseEntity<VisitLog> getVisitLog(@PathVariable Long id) {
        return ResponseEntity.ok(visitLogService.getVisitLog(id));
    }
}

VisitorController.java

package com.example.demo.controller;

import com.example.demo.model.Visitor;
import com.example.demo.service.VisitorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@Tag(name = "Visitor")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) {
        return ResponseEntity.ok(visitorService.createVisitor(visitor));
    }

    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitor(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getVisitor(id));
    }
}

EXCEPTION


ApiExceptionHandler.java

package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

BadRequestException.java

package com.example.demo.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

Resourcenotfoundexception.java

package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

REPOSITORY

AlertNotificationRepo

package com.example.demo.repository;

import com.example.demo.model.AlertNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertNotificationRepository extends JpaRepository<AlertNotification, Long> {

        List<AlertNotification> findByVisitLogId(Long id);
}

AppointmentRepo
package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // exact naming required
    List<Appointment> findByVisitorId(Long id);

    List<Appointment> findByHostId(Long id);
}


HostRepo.java
package com.example.demo.repository;

import com.example.demo.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {

    // for uniqueness checks
    Optional<Host> findByEmail(String email);
}

UserRepo

package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}

VisitLogRepo
package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    // exact naming required in your sheet
    List<VisitLog> findByCheckoutTimeIsNull();
}

VisitorRepo
package com.example.demo.repository;

import com.example.demo.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}

SERVICE

package com.example.demo.service;

import com.example.demo.model.AlertNotification;
import java.util.List;

public interface AlertNotificationService {
    AlertNotification sendAlert(Long visitLogId);
    AlertNotification getAlert(Long id);
    List<AlertNotification> getAllAlerts();
}


package com.example.demo.service;

import com.example.demo.model.Appointment;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Long visitorId, Long hostId, Appointment appointment);
    Appointment getAppointment(Long id);
    List<Appointment> getAppointmentsForHost(Long hostId);
    List<Appointment> getAppointmentsForVisitor(Long visitorId);
}


package com.example.demo.service;

import com.example.demo.model.Host;
import java.util.List;

public interface HostService {
    Host createHost(Host host);
    Host getHost(Long id);
    List<Host> getAllHosts();
}


package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {
    User register(User user);
    String login(String username, String password);
    User createUser(User user);
    User getUser(Long id);
    User getByUsername(String username);
    List<User> getAllUsers();
}


package com.example.demo.service;

import com.example.demo.model.VisitLog;
import java.util.List;

public interface VisitLogService {
    VisitLog checkInVisitor(Long visitorId, Long hostId, String purpose);
    VisitLog checkOutVisitor(Long visitLogId);
    List<VisitLog> getActiveVisits();
    VisitLog getVisitLog(Long id);
}


package com.example.demo.service;

import com.example.demo.model.Visitor;
import java.util.List;

public interface VisitorService {
    Visitor createVisitor(Visitor visitor);
    Visitor getVisitor(Long id);
    List<Visitor> getAllVisitors();
}


IMPL:

package com.example.demo.service.impl;

import com.example.demo.model.AlertNotification;
import com.example.demo.model.VisitLog;
import com.example.demo.repository.AlertNotificationRepository;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.service.AlertNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public AlertNotification sendAlert(Long visitLogId) {
        VisitLog log = visitLogRepository.findById(visitLogId).orElse(null);
        if (log == null) {
            return null;
        }

        if (Boolean.TRUE.equals(log.getAlertSent())) {
            // already sent; just return any existing alert if present
            List<AlertNotification> existing = alertRepository.findByVisitLogId(visitLogId);
            return existing.isEmpty() ? null : existing.get(0);
        }

        AlertNotification alert = new AlertNotification();
        alert.setVisitLog(log);
        alert.setSentTo(log.getHost().getEmail());
        alert.setAlertMessage("Visitor check-in alert");

        log.setAlertSent(true);
        visitLogRepository.save(log);

        return alertRepository.save(alert);
    }

    @Override
    public AlertNotification getAlert(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    @Override
    public List<AlertNotification> getAllAlerts() {
        return alertRepository.findAll();
    }
}


package com.example.demo.service.impl;

import com.example.demo.model.Appointment;
import com.example.demo.model.Host;
import com.example.demo.model.Visitor;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public Appointment createAppointment(Long visitorId, Long hostId, Appointment appointment) {
        Visitor visitor = visitorRepository.findById(visitorId).orElse(null);
        Host host = hostRepository.findById(hostId).orElse(null);

        if (visitor == null || host == null) {
            return null;
        }

        LocalDateTime date = appointment.getAppointmentDate();
        if (date != null && date.isBefore(LocalDateTime.now())) {
            // no exception; just return null to indicate invalid
            return null;
        }

        appointment.setVisitor(visitor);
        appointment.setHost(host);
        if (appointment.getStatus() == null) {
            appointment.setStatus(Appointment.Status.SCHEDULED);
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAppointmentsForHost(Long hostId) {
        return appointmentRepository.findByHostId(hostId);
    }

    @Override
    public List<Appointment> getAppointmentsForVisitor(Long visitorId) {
        return appointmentRepository.findByVisitorId(visitorId);
    }
}


package com.example.demo.service.impl;

import com.example.demo.model.Host;
import com.example.demo.repository.HostRepository;
import com.example.demo.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public Host createHost(Host host) {
        return hostRepository.save(host);
    }

    @Override
    public Host getHost(Long id) {
        // returns null if not found instead of throwing exception
        return hostRepository.findById(id).orElse(null);
    }

    @Override
    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }
}


package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // password stored as plain text (no security)
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public String login(String username, String password) {
          throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}

package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // password stored as plain text (no security)
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public String login(String username, String password) {
          throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}


package com.example.demo.service.impl;

import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public Visitor createVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor getVisitor(Long id) {
        return visitorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
}


