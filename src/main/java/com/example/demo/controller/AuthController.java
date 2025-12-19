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