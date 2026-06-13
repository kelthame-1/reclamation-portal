package com.example.reclamations_portal.controller;

import com.example.reclamations_portal.model.Categorie;
import com.example.reclamations_portal.service.CategorieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("categorie", new Categorie());
        return "categories-liste";
    }

    @PostMapping
    public String ajouter(@ModelAttribute Categorie categorie) {
        categorieService.save(categorie);
        return "redirect:/categories";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        categorieService.deleteById(id);
        return "redirect:/categories";
    }
}

