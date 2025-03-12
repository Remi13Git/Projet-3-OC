package com.openclassrooms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Configuration pour servir le dossier uploads via /uploads
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/");  // Dossier uploads dans le backend
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**") // Permet l'accès à toutes les routes
                .allowedOrigins("http://localhost:4200") // Le frontend sur le port 4200
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Autorise les méthodes HTTP
    }
}
