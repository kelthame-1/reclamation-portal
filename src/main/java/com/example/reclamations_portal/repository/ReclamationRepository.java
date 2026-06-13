package com.example.reclamations_portal.repository;

import com.example.reclamations_portal.model.Reclamation;
import com.example.reclamations_portal.model.StatutReclamation;
import com.example.reclamations_portal.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    List<Reclamation> findByClient(Utilisateur client);

    List<Reclamation> findByAgent(Utilisateur agent);

    List<Reclamation> findByStatut(StatutReclamation statut);
}

