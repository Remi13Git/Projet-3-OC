package com.openclassrooms.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.openclassrooms.models.Message;
import com.openclassrooms.repositories.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Enregistrer le message dans la base de données
    public Message saveMessage(Message message) {
        // Définir les timestamps pour created_at et updated_at
        LocalDateTime now = LocalDateTime.now();
        message.setCreatedAt(now);
        message.setUpdatedAt(now);  // La première mise à jour est égale à la création

        return messageRepository.save(message);
    }
}
