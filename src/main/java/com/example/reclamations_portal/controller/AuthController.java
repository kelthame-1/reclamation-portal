package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.Utilisateur;
import com.example.reclamations_portal.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(org.springframework.ui.Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.enregistrer(utilisateur);
        return "redirect:/login";
    }
}

