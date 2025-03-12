package com.openclassrooms.controllers;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.AuthResponse;
import com.openclassrooms.dto.LoginRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.models.MyUser;
import com.openclassrooms.services.JWTService;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final JWTService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JWTService jwtService, UserService userService, AuthenticationManager authenticationManager) { 
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public AuthResponse getToken(@RequestBody LoginRequest loginRequest) {
        // Essayer d'authentifier l'utilisateur
        try {
            // Créer un objet d'authentification basé sur les informations de la requête de login
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            );

            // Utilisation de AuthenticationManager pour authentifier l'utilisateur
            authentication = authenticationManager.authenticate(authentication);

            // Si l'authentification réussit, on génère le token
            String token = jwtService.generateToken(authentication);

            // Retourner un objet avec le token
            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            // Si l'authentification échoue, on lance une exception
            throw new RuntimeException("Identifiants incorrects");
        }
    }

    // Route pour l'inscription /api/auth/register
    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        // Vérifier si l'utilisateur existe déjà
        if (userService.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("Cet utilisateur existe déjà !");
        }

        // Créer un nouvel utilisateur
        MyUser newUser = new MyUser();
        newUser.setEmail(registerRequest.getEmail()); // Utilisation de l'email
        newUser.setPassword(registerRequest.getPassword()); 
        newUser.setName(registerRequest.getName()); // Utilisation du nom
        newUser.setCreatedAt(LocalDateTime.now()); // Utilisation de la date actuelle
        newUser.setUpdatedAt(LocalDateTime.now()); // Date de mise à jour

        // Enregistrer l'utilisateur en base
        userService.registerUser(newUser);

        // Générer un token JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            newUser.getEmail(), registerRequest.getPassword()
        );
        String token = jwtService.generateToken(authentication);

        return new AuthResponse(token);
    }

    // Route pour récupérer les infos de l'utilisateur connecté /api/auth/me
    @GetMapping("/me")
    public Map<String, Object> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        // Vérification des claims du JWT
        String email = jwt.getSubject();

        // Récupérer l'utilisateur par son email
        MyUser user = userService.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        // Retourner les infos de l'utilisateur
        return Map.of(
            "id", user.getId(),
            "name", user.getName() != null ? user.getName() : "Utilisateur inconnu", // Vérification si le 'name' est null
            "email", user.getEmail() != null ? user.getEmail() : "Email inconnu", // Vérification si le 'email' est null
            "created_at", user.getCreatedAt().toString(),
            "updated_at", user.getUpdatedAt().toString()
        );
    }
}
