package com.openclassrooms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.dto.MessageRequest;
import com.openclassrooms.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Create a new message", description = "Authenticated users can send messages related to a rental.")
    @PostMapping
    public ResponseEntity<Object> createMessage(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal Jwt jwt) {
        return messageService.createMessage(messageRequest, jwt);
    }
}
