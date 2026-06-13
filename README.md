<div align="center">

# 🎫 Portail de Gestion des Réclamations Clients

**Système de ticketing pour soumettre, traiter et suivre des réclamations clients**

</div>

---

## 📋 Description

Portail web permettant aux clients de soumettre des réclamations, aux agents de les traiter, et aux superviseurs de les gérer — avec un workflow complet et une interface moderne.

---

## ✨ Fonctionnalités

- 🔐 **Authentification** — Inscription et connexion sécurisée
- 👥 **3 Rôles** — Client / Agent / Superviseur
- 📝 **Gestion des réclamations** — Soumettre, suivre et gérer
- 🔄 **Workflow** — Ouverte → En traitement → Résolue → Fermée
- 🔍 **Recherche** — Par titre, statut, catégorie, client et date
- 📊 **Tableau de bord** — KPIs en temps réel
- 🏷️ **Catégories** — Classification des réclamations
- 💬 **Réponses** — Messagerie sur chaque réclamation
- 🛡️ **Sécurité** — Contrôle d'accès par rôle

---

## 🛠️ Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8
- Bootstrap 5

---

## 📁 Structure du projet
Src/main/java/com/example/reclamations_portal/
├── config/        # Spring Security + GlobalControllerAdvice
├── controller/    # Auth, Dashboard, Réclamations, Catégories, Utilisateurs, Réponses
├── model/         # Utilisateur, Réclamation, Réponse, Catégorie, Role, StatutReclamation
├── repository/    # Repositories Spring Data JPA
└── service/       # Logique métier
