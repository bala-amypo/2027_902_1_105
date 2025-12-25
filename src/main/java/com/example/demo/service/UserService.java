package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    AuthResponse authenticate(AuthRequest request);

    User registerUser(AuthRequest request);

    List<User> getAllUsers();

    User createUser(User user);

    User getUser(Long id);

    User getByUsername(String username);
}
