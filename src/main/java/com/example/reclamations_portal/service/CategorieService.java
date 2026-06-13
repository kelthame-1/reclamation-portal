package com.example.reclamations_portal.service;

import com.example.reclamations_portal.model.Categorie;
import com.example.reclamations_portal.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Categorie findById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
    }

    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }
}
