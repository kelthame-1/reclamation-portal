package com.example.reclamations_portal.service;

import com.example.reclamations_portal.model.Role;
import com.example.reclamations_portal.model.Utilisateur;
import com.example.reclamations_portal.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public Utilisateur enregistrer(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        if (utilisateurRepository.count() == 0) {
            utilisateur.setRole(Role.SUPERVISEUR);
        } else {
            utilisateur.setRole(Role.CLIENT);
        }

        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public List<Utilisateur> findByRole(Role role) {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .toList();
    }

    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}

