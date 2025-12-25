package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;

public interface UserService {
    User registerUser(AuthRequest request);
    AuthResponse login(AuthRequest request);
    User createUser(User user);
    User getUser(Long id);
    User getByUsername(String username);
    List<User> getAllUsers();
}