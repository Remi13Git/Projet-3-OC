package com.openclassrooms.services;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.ApiResponse;
import com.openclassrooms.dto.MessageRequest;
import com.openclassrooms.models.Message;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.repositories.MessageRepository;
import com.openclassrooms.repositories.UserRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> createMessage(MessageRequest messageRequest, Jwt jwt) {
        try {
            // Récupérer l'utilisateur via le JWT
            String userEmail = jwt.getSubject();
            MyUser user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(403).body(new ApiResponse.ErrorResponse("Utilisateur non trouvé."));
            }

            // Création du message
            Message message = new Message();
            message.setRentalId(messageRequest.getRentalId());
            message.setUserId(user.getId());
            message.setMessage(messageRequest.getMessage());
            message.setCreatedAt(LocalDateTime.now());
            message.setUpdatedAt(LocalDateTime.now());

            // Sauvegarde en base
            messageRepository.save(message);

            return ResponseEntity.ok(new ApiResponse.SuccessResponse("Message send with success"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse.ErrorResponse("Erreur interne du serveur."));
        }
    }
}
