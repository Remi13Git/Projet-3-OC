package com.openclassrooms.dto;

public class AuthResponse {
    private String token;

    // Getters et setters
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
