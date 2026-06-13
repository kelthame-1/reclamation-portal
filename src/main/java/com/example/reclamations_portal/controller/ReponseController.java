package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.*;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import com.example.reclamations_portal.service.ReclamationService;
import com.example.reclamations_portal.service.ReponseService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reponses")
public class ReponseController {

    private final ReponseService reponseService;
    private final ReclamationService reclamationService;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping
    public String ajouter(@RequestParam Long reclamationId,
                          @RequestParam String contenu,
                          Authentication authentication) {

        Utilisateur auteur = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow();

        Reclamation reclamation = reclamationService.findById(reclamationId);

        Reponse reponse = new Reponse();
        reponse.setContenu(contenu);
        reponse.setReclamation(reclamation);
        reponse.setAuteur(auteur);

        reponseService.ajouter(reponse);

        return "redirect:/reclamations/" + reclamationId;
    }
}