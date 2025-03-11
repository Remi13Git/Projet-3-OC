package com.openclassrooms.controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.RentalsResponse;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.models.Rental;
import com.openclassrooms.repositories.UserRepository;
import com.openclassrooms.services.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserRepository userRepository;

    public RentalController(RentalService rentalService, UserRepository userRepository) {
        this.rentalService = rentalService;
        this.userRepository = userRepository;
    }

    // Route GET /rentals
    @GetMapping
    public ResponseEntity<RentalsResponse> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();  // Récupérer les locations

        if (rentals.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si aucune location n'est trouvée
        }

        // Retourner la réponse en utilisant le DTO
        RentalsResponse rentalsResponse = new RentalsResponse(rentals);
        return ResponseEntity.ok(rentalsResponse); // Retourner le DTO
    }


    // Route GET /rentals/:id
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Route POST /rentals (Création d'une location avec gestion d'image)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Rental> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture") MultipartFile picture,
            @AuthenticationPrincipal Jwt jwt) {

        try {
            // Récupérer l'email depuis le JWT
            String userEmail = jwt.getSubject();
            System.out.println("Email extrait du JWT : " + userEmail);

            // Rechercher l'utilisateur en base par email
            MyUser user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(403).body(null); // Utilisateur non trouvé
            }

            Long ownerId = user.getId(); // Récupérer l'ID
            System.out.println("Owner ID récupéré depuis la base de données : " + ownerId);

            // Création de l'objet Rental
            Rental rental = new Rental();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setOwnerId(ownerId);

            // Gestion de l'image
            if (picture != null && !picture.isEmpty()) {
                String uploadDir = "uploads/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                String fileName = picture.getOriginalFilename();
                Path filePath = Path.of(uploadDir, fileName);
                Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String absoluteUrl = "http://localhost:3001/uploads/" + fileName;
                rental.setPicture(absoluteUrl);
            }

            // Sauvegarde en base
            Rental createdRental = rentalService.createRental(rental);
            return ResponseEntity.ok(createdRental);

        } catch (IOException | IllegalArgumentException  e) {
        return ResponseEntity.status(500).body(null);
    }
    }


    // Route PUT /rentals/:id (Mise à jour d'une location)
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @AuthenticationPrincipal Jwt jwt) {

        try {

            // Récupérer la location par son ID
            Optional<Rental> rentalOptional = rentalService.getRentalById(id);

            // Vérifier si la location existe
            if (!rentalOptional.isPresent()) {
                return ResponseEntity.notFound().build(); // Si la location n'existe pas
            }

            Rental existingRental = rentalOptional.get();  // Extraire l'objet Rental de l'Optional


            // Mise à jour des propriétés de la location
            existingRental.setName(name);
            existingRental.setSurface(surface);
            existingRental.setPrice(price);
            existingRental.setDescription(description);

            // Mise à jour de la location en base de données
            Optional<Rental> updatedRentalOptional = rentalService.updateRental(id, existingRental);

            // Si la mise à jour est réussie, retourner la location mise à jour
            if (updatedRentalOptional.isPresent()) {
                return ResponseEntity.ok(updatedRentalOptional.get());
            } else {
                return ResponseEntity.status(500).build();  // Erreur interne si l'update échoue
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
