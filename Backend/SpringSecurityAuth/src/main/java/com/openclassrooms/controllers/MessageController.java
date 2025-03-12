package com.openclassrooms.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.ApiResponse;
import com.openclassrooms.dto.MessageRequest;
import com.openclassrooms.models.Message;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.repositories.UserRepository;
import com.openclassrooms.services.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createMessage(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal Jwt jwt) {

        try {
            // Récupérer l'email depuis le JWT
            String userEmail = jwt.getSubject();
            System.out.println("Email extrait du JWT : " + userEmail);

            // Trouver l'utilisateur en base
            MyUser user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(403).body(new ApiResponse.ErrorResponse("Utilisateur non trouvé."));
            }

            Long userId = user.getId();
            System.out.println("User ID récupéré : " + userId);

            // Créer le message
            Message message = new Message();
            message.setRentalId(messageRequest.getRentalId());
            message.setUserId(userId);
            message.setMessage(messageRequest.getMessage());
            message.setCreatedAt(LocalDateTime.now());
            message.setUpdatedAt(LocalDateTime.now());

            // Sauvegarde en DB
            messageService.saveMessage(message);

            // Retourner un message de validation avec une structure JSON
            return ResponseEntity.ok(new ApiResponse.SuccessResponse("Message send with success"));

        } catch (Exception e) {
            System.err.println("Erreur lors de la création du message : " + e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse.ErrorResponse("Erreur interne du serveur."));
        }
    }

    
}
