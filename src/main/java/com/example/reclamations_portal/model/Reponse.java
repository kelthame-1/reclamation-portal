package com.example.reclamations_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reponses")
@Data
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String contenu;

    private LocalDateTime dateReponse = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "reclamation_id")
    private Reclamation reclamation;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Utilisateur auteur;
}

