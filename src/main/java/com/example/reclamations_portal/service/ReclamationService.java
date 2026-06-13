package com.example.reclamations_portal.service;

import com.example.reclamations_portal.model.*;
import com.example.reclamations_portal.repository.ReclamationRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository reclamationRepository;

    public Reclamation creer(Reclamation reclamation) {
        reclamation.setStatut(StatutReclamation.OUVERTE);
        reclamation.setDateCreation(LocalDateTime.now());
        return reclamationRepository.save(reclamation);
    }

    public List<Reclamation> findAll() {
        return reclamationRepository.findAll();
    }

    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
    }

    public List<Reclamation> findByClient(Utilisateur client) {
        return reclamationRepository.findByClient(client);
    }

    public List<Reclamation> findByAgent(Utilisateur agent) {
        return reclamationRepository.findByAgent(agent);
    }

    public List<Reclamation> findByStatut(StatutReclamation statut) {
        return reclamationRepository.findByStatut(statut);
    }

    public Reclamation assignerAgent(Long reclamationId, Utilisateur agent) {
        Reclamation r = findById(reclamationId);
        r.setAgent(agent);
        r.setStatut(StatutReclamation.EN_TRAITEMENT);
        return reclamationRepository.save(r);
    }

    public Reclamation changerStatut(Long reclamationId, StatutReclamation nouveauStatut) {
        Reclamation r = findById(reclamationId);
        r.setStatut(nouveauStatut);
        if (nouveauStatut == StatutReclamation.RESOLUE) {
            r.setDateResolution(LocalDateTime.now());
        }
        return reclamationRepository.save(r);
    }
}

