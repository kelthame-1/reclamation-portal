package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.Utilisateur;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final UtilisateurRepository utilisateurRepository;

    @ModelAttribute("currentUser")
    public Utilisateur utilisateurConnecte(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return utilisateurRepository.findByEmail(authentication.getName())
                .orElse(null);
    }
    @ModelAttribute("currentPath")
    public String currentPath(HttpServletRequest request) {
        return request.getRequestURI();
    }
}