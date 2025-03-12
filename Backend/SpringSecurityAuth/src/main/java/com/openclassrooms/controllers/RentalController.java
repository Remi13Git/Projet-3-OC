package com.openclassrooms.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.dto.RentalsResponse;
import com.openclassrooms.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "BearerAuth")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Get all rentals", description = "Retrieve a list of all rentals")
    @GetMapping
    public ResponseEntity<RentalsResponse> getAllRentals() {
        List<RentalDTO> rentals = rentalService.getAllRentals();
        return rentals.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(new RentalsResponse(rentals));
    }

    @Operation(summary = "Get a rental by ID", description = "Retrieve details of a rental by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new rental", description = "Create a new rental with an image upload")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Object> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture") MultipartFile picture,
            @AuthenticationPrincipal Jwt jwt) {
        return rentalService.createRental(name, surface, price, description, picture, jwt);
    }

    @Operation(summary = "Update a rental", description = "Update rental details by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @AuthenticationPrincipal Jwt jwt) {
        return rentalService.updateRental(id, name, surface, price, description);
    }
}
