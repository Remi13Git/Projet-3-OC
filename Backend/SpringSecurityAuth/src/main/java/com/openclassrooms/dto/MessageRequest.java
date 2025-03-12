package com.openclassrooms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {

    @JsonProperty("rental_id")
    private Long rentalId;  // L'ID de la location

    @JsonProperty("user_id")
    private Long userId;    // L'ID de l'utilisateur
    
    private String message; // Le message envoyé

    // Constructeur
    public MessageRequest() {}

    // Getters et Setters
    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
