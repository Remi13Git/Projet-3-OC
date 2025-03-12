package com.openclassrooms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Route pour récupérer les informations de l'utilisateur par ID
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        // Récupérer l'utilisateur par son ID
        MyUser user = userService.findById(id);

        // Si l'utilisateur n'est pas trouvé, on retourne une exception 404
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        // Retourner l'utilisateur sous forme de UserDTO
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
