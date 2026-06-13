package com.example.reclamations_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamations")
@Data
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private StatutReclamation statut = StatutReclamation.OUVERTE;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private LocalDateTime dateResolution;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Utilisateur client;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Utilisateur agent;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}

