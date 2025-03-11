package com.openclassrooms.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.models.Rental;
import com.openclassrooms.repositories.RentalRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental) {
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

    public Optional<Rental> updateRental(Long id, Rental updatedRental) {
        return rentalRepository.findById(id).map(rental -> {
            rental.setName(updatedRental.getName());
            rental.setSurface(updatedRental.getSurface());
            rental.setPrice(updatedRental.getPrice());
            rental.setPicture(updatedRental.getPicture());
            rental.setDescription(updatedRental.getDescription());
            rental.setUpdatedAt(LocalDateTime.now());
            return rentalRepository.save(rental);
        });
    }
}
