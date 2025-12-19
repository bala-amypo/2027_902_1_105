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
