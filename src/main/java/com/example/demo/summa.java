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


