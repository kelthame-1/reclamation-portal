package com.example.reclamations_portal.repository;

import com.example.reclamations_portal.model.Reponse;
import com.example.reclamations_portal.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {

    List<Reponse> findByReclamation(Reclamation reclamation);
}

