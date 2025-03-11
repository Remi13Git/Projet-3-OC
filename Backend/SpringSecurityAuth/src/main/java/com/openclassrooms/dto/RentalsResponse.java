package com.openclassrooms.dto;

import java.util.List;

import com.openclassrooms.models.Rental;

public class RentalsResponse {

    private List<Rental> rentals;

    // Constructeur
    public RentalsResponse(List<Rental> rentals) {
        this.rentals = rentals;
    }

    // Getter et Setter
    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
