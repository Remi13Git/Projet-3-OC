package com.openclassrooms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.models.MyUser;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api") // Corriger ici
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Route pour récupérer les informations de l'utilisateur par ID
    @GetMapping("/user/{id}")
    public MyUser getUserById(@PathVariable Long id) {
        // Récupérer l'utilisateur par son ID
        MyUser user = userService.findById(id);

        // Si l'utilisateur n'est pas trouvé, on retourne une exception 404
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        return user;
    }
}
