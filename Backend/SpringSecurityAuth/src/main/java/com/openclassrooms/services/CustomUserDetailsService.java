package com.openclassrooms.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.models.MyUser;
import com.openclassrooms.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche l'utilisateur dans la base de données par son email
        MyUser user = userRepository.findByEmail(email);
        
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        
        // Retourner un objet UserDetails avec l'utilisateur trouvé et ses rôles
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
