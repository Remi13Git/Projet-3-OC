package com.openclassrooms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.models.MyUser;
import com.openclassrooms.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(MyUser user) {
        // Vérification si l'utilisateur existe déjà
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Utilisateur déjà existant");
        }

        // Hachage du mot de passe avant l'enregistrement
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Enregistrer l'utilisateur dans la base de données
        userRepository.save(user);
    }

    public MyUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Méthode pour trouver un utilisateur par ID
    public MyUser findById(Long id) {
        return userRepository.findById(id).orElse(null); // Retourne null si l'utilisateur n'est pas trouvé
    }
}
