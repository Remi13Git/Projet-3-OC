package com.openclassrooms.dto;

import java.util.List;

public class RentalsResponse {

    private List<RentalDTO> rentals;

    // Constructeur
    public RentalsResponse(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }

    // Getter et setter
    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }
}
