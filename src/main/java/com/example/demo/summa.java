MODEL

AlertNotification.java


Appointment.java



Host.java


User.java



VisitLog.java



Visitor.java


CONTROLLER:

AlertNotificationc.java


Appointmentcontroller.java



AuthController.java



HostController.java



VisitLogController.java



VisitorController.java


EXCEPTION


ApiExceptionHandler.java



BadRequestException.java

Resourcenotfoundexception.java



REPOSITORY

AlertNotificationRepo


AppointmentRepo


HostRepo.java

UserRepo


VisitLogRepo


VisitorRepo


SERVICE














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


