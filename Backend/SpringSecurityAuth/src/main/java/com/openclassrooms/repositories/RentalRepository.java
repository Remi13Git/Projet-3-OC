package com.openclassrooms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.models.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByOwnerId(Long ownerId);
}
