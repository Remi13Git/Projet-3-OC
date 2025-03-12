package com.openclassrooms.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.ApiResponse;
import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.models.Rental;
import com.openclassrooms.repositories.RentalRepository;
import com.openclassrooms.repositories.UserRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "uploads/";

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RentalDTO> getRentalById(Long id) {
        return rentalRepository.findById(id).map(this::convertToDTO);
    }

    public ResponseEntity<Object> createRental(String name, BigDecimal surface, BigDecimal price, 
                                               String description, MultipartFile picture, Jwt jwt) {
        try {
            String userEmail = jwt.getSubject();
            MyUser user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(403).body(new ApiResponse.ErrorResponse("Utilisateur non trouvé"));
            }

            Rental rental = new Rental();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setOwnerId(user.getId());
            rental.setCreatedAt(LocalDateTime.now());
            rental.setUpdatedAt(LocalDateTime.now());

            if (picture != null && !picture.isEmpty()) {
                rental.setPicture(savePicture(picture));
            }

            rentalRepository.save(rental);
            return ResponseEntity.ok(new ApiResponse.SuccessResponse("Rental created !"));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse.ErrorResponse("Erreur interne lors de l'upload"));
        }
    }

    public ResponseEntity<Object> updateRental(Long id, String name, BigDecimal surface, BigDecimal price, 
                                               String description) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setUpdatedAt(LocalDateTime.now());
            rentalRepository.save(rental);
            return ResponseEntity.ok(new ApiResponse.SuccessResponse("Rental updated !"));
        }

        return ResponseEntity.status(404).body(new ApiResponse.ErrorResponse("Rental not found"));
    }

    private String savePicture(MultipartFile picture) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String uuidFileName = UUID.randomUUID().toString() + "-" + picture.getOriginalFilename();
        Path filePath = Path.of(UPLOAD_DIR, uuidFileName);
        Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "http://localhost:3001/uploads/" + uuidFileName;
    }

    private RentalDTO convertToDTO(Rental rental) {
        return new RentalDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getDescription(),
                rental.getPicture(),
                rental.getOwnerId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }
}
