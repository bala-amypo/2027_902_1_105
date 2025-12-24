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