package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.*;
import com.example.reclamations_portal.repository.CategorieRepository;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import com.example.reclamations_portal.service.CategorieService;
import com.example.reclamations_portal.service.ReclamationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.beans.PropertyEditorSupport;
import java.util.List;
import com.example.reclamations_portal.service.ReponseService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reclamations")
public class ReclamationController {

    private final ReclamationService reclamationService;
    private final CategorieService categorieService;
    private final CategorieRepository categorieRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ReponseService reponseService;

    private Utilisateur getUtilisateurConnecte(Authentication authentication) {
        return utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Categorie.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                } else {
                    Categorie categorie = categorieRepository.findById(Long.parseLong(text))
                            .orElse(null);
                    setValue(categorie);
                }
            }
        });
    }

    @GetMapping
    public String liste(Model model, Authentication authentication,
                        @RequestParam(required = false) StatutReclamation statut,
                        @RequestParam(required = false) Long categorieId,
                        @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") java.time.LocalDate dateDebut,
                        @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") java.time.LocalDate dateFin,
                        @RequestParam(required = false) Long clientId,
                        @RequestParam(required = false) String search) {
        Utilisateur utilisateur = getUtilisateurConnecte(authentication);

        List<Reclamation> reclamations;
        switch (utilisateur.getRole()) {
            case CLIENT -> reclamations = reclamationService.findByClient(utilisateur);
            case AGENT -> reclamations = reclamationService.findByAgent(utilisateur);
            default -> reclamations = reclamationService.findAll();
        }

        if (statut != null) {
            reclamations = reclamations.stream()
                    .filter(r -> r.getStatut() == statut)
                    .toList();
        }

        if (categorieId != null) {
            reclamations = reclamations.stream()
                    .filter(r -> r.getCategorie() != null && r.getCategorie().getId().equals(categorieId))
                    .toList();
        }
        if (dateDebut != null) {
            reclamations = reclamations.stream()
                    .filter(r -> !r.getDateCreation().toLocalDate().isBefore(dateDebut))
                    .toList();
        }

        if (dateFin != null) {
            reclamations = reclamations.stream()
                    .filter(r -> !r.getDateCreation().toLocalDate().isAfter(dateFin))
                    .toList();
        }
        if (clientId != null) {
            reclamations = reclamations.stream()
                    .filter(r -> r.getClient() != null && r.getClient().getId().equals(clientId))
                    .toList();
        }

        if (search != null && !search.isEmpty()) {
            reclamations = reclamations.stream()
                    .filter(r -> r.getTitre().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }

        model.addAttribute("reclamations", reclamations);
        model.addAttribute("statuts", StatutReclamation.values());
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("statutFiltre", statut);
        model.addAttribute("categorieFiltre", categorieId);
        model.addAttribute("dateDebutFiltre", dateDebut);
        model.addAttribute("dateFinFiltre", dateFin);
        model.addAttribute("clientFiltre", clientId);
        model.addAttribute("searchFiltre", search);

        if (utilisateur.getRole() != Role.CLIENT) {
            model.addAttribute("clients", utilisateurRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.CLIENT)
                    .toList());
        }

        return "reclamations-liste";
    }

    @GetMapping("/nouvelle")
    public String formulaireNouvelle(Model model) {
        model.addAttribute("reclamation", new Reclamation());
        model.addAttribute("categories", categorieService.findAll());
        return "reclamation-form";
    }

    @PostMapping
    public String creer(@ModelAttribute Reclamation reclamation, Authentication authentication) {
        Utilisateur client = getUtilisateurConnecte(authentication);
        reclamation.setClient(client);
        reclamationService.creer(reclamation);
        return "redirect:/reclamations";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model, Authentication authentication) {
        Reclamation reclamation = reclamationService.findById(id);
        Utilisateur utilisateur = getUtilisateurConnecte(authentication);

        boolean autorise = switch (utilisateur.getRole()) {
            case SUPERVISEUR -> true;
            case CLIENT -> reclamation.getClient() != null && reclamation.getClient().getId().equals(utilisateur.getId());
            case AGENT -> reclamation.getAgent() != null && reclamation.getAgent().getId().equals(utilisateur.getId());
        };

        if (!autorise) {
            throw new org.springframework.security.access.AccessDeniedException("Accès refusé à cette réclamation");
        }

        model.addAttribute("reclamation", reclamation);
        model.addAttribute("reponses", reponseService.findByReclamation(reclamation));
        model.addAttribute("nouvelleReponse", new Reponse());
        model.addAttribute("agents", utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.AGENT)
                .toList());
        model.addAttribute("statuts", StatutReclamation.values());

        return "reclamation-details";
    }

    @PostMapping("/{id}/assigner")
    public String assignerAgent(@PathVariable Long id, @RequestParam Long agentId) {
        Utilisateur agent = utilisateurRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent non trouvé"));
        reclamationService.assignerAgent(id, agent);
        return "redirect:/reclamations/" + id;
    }
    @PostMapping("/{id}/statut")
    public String changerStatut(@PathVariable Long id, @RequestParam StatutReclamation statut) {
        reclamationService.changerStatut(id, statut);
        return "redirect:/reclamations/" + id;
    }
}
