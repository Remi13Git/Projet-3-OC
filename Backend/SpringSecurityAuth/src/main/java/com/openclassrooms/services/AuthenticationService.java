package com.openclassrooms.services;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.AuthResponse;
import com.openclassrooms.dto.LoginRequest;
import com.openclassrooms.dto.RegisterRequest;
import com.openclassrooms.models.MyUser;

@Service
public class AuthenticationService {

    private final JWTService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JWTService jwtService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // Méthode pour récupérer les informations de l'utilisateur authentifié
    public Map<String, Object> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        String email = authentication.getName();
        MyUser user = userService.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        return Map.of(
                "id", user.getId(),
                "name", user.getName() != null ? user.getName() : "Utilisateur inconnu",
                "email", user.getEmail() != null ? user.getEmail() : "Email inconnu",
                "created_at", user.getCreatedAt().toString(),
                "updated_at", user.getUpdatedAt().toString()
        );
    }

    // Méthode pour gérer la connexion
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            // Authentifier l'utilisateur
            authentication = authenticationManager.authenticate(authentication);

            // Générer le token JWT
            String token = jwtService.generateToken(authentication);

            // Retourner la réponse contenant le token
            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Identifiants incorrects");
        }
    }

    // Méthode pour gérer l'enregistrement
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userService.userExists(registerRequest.getEmail())) {
            throw new RuntimeException("Cet utilisateur existe déjà !");
        }

        // Créer un nouvel utilisateur
        MyUser newUser = new MyUser();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setName(registerRequest.getName());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        // Enregistrer l'utilisateur
        userService.registerUser(newUser);

        // Créer un token JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                newUser.getEmail(), registerRequest.getPassword()
        );
        String token = jwtService.generateToken(authentication);

        return new AuthResponse(token);
    }
}
