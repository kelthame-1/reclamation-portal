package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.StatutReclamation;
import com.example.reclamations_portal.model.Utilisateur;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import com.example.reclamations_portal.service.ReclamationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ReclamationService reclamationService;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        Utilisateur utilisateur = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        long nbOuvertes = reclamationService.findByStatut(StatutReclamation.OUVERTE).size();
        long nbEnTraitement = reclamationService.findByStatut(StatutReclamation.EN_TRAITEMENT).size();
        long nbResolues = reclamationService.findByStatut(StatutReclamation.RESOLUE).size();
        long nbFermees = reclamationService.findByStatut(StatutReclamation.FERMEE).size();

        model.addAttribute("nbOuvertes", nbOuvertes);
        model.addAttribute("nbEnTraitement", nbEnTraitement);
        model.addAttribute("nbResolues", nbResolues);
        model.addAttribute("nbFermees", nbFermees);

        List<?> reclamations;
        switch (utilisateur.getRole()) {
            case CLIENT -> reclamations = reclamationService.findByClient(utilisateur);
            case AGENT -> reclamations = reclamationService.findByAgent(utilisateur);
            default -> reclamations = reclamationService.findAll();
        }
        model.addAttribute("reclamations", reclamations);

        return "dashboard";
    }
}

