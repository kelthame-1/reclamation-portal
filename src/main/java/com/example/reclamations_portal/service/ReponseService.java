package com.example.reclamations_portal.service;

import com.example.reclamations_portal.model.Reponse;
import com.example.reclamations_portal.model.Reclamation;
import com.example.reclamations_portal.repository.ReponseRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReponseService {

    private final ReponseRepository reponseRepository;

    public Reponse ajouter(Reponse reponse) {
        reponse.setDateReponse(LocalDateTime.now());
        return reponseRepository.save(reponse);
    }

    public List<Reponse> findByReclamation(Reclamation reclamation) {
        return reponseRepository.findByReclamation(reclamation);
    }
}

