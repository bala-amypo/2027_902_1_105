package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private String role;
    private Long userId;
    private String email;

    // No-argument constructor
    public AuthResponse() {}

    // Single-argument constructor (token only)
    public AuthResponse(String token) {
        this.token = token;
    }

    // Full constructor
    public AuthResponse(String token, String role, Long userId, String email) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.email = email;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
