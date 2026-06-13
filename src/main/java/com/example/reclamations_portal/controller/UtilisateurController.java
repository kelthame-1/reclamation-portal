package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.Role;
import com.example.reclamations_portal.model.Utilisateur;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import com.example.reclamations_portal.service.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        model.addAttribute("roles", Role.values());
        return "utilisateurs-liste";
    }

    @PostMapping("/{id}/role")
    public String changerRole(@PathVariable Long id, @RequestParam Role role) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        utilisateur.setRole(role);
        utilisateurRepository.save(utilisateur);
        return "redirect:/utilisateurs";
    }
}